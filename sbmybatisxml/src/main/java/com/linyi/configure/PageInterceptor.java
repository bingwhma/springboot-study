package com.linyi.configure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;


@Intercepts({
    @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
            RowBounds.class, ResultHandler.class }),
    @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class PageInterceptor implements Interceptor {
	
	
    final static int MAPPED_STATEMENT_INDEX = 0;
    
    final static int PARAMETER_INDEX = 1;
    
    final static int ROWBOUNDS_INDEX = 2;
    
    final static int RESULT_HANDLER_INDEX = 3;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

    	System.out.println("==========================intercept Invocation=======");
        Page<?> pageRequest = null;
        final Object[] queryArgs = invocation.getArgs();

        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        final BoundSql boundSql = ms.getBoundSql(parameter);

        pageRequest = this.findPageableObject(parameter);
        String param = fetchSQLParam(ms, boundSql);

        if (pageRequest != null) {

            int total = this.queryTotal(ms, boundSql, param);
            
            System.out.println("=================count:" + total);
            pageRequest.setTotalRecord(total);

            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);

            BoundSql pageSQL = copyFromBoundSql(ms, boundSql, getPageSql(pageRequest, boundSql.getSql()));
            MappedStatement pageMS = copyFromMappedStatement(ms, new BoundSqlSqlSource(pageSQL));
            queryArgs[MAPPED_STATEMENT_INDEX] = pageMS;

            String sql = pageSQL.getSql().replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
            if (sql != null && (sql.toUpperCase().startsWith("INSERT") || sql.toUpperCase().startsWith("UPDATE") || sql.toUpperCase().startsWith("DELETE"))) {
            }
        } else {
            String sql = boundSql.getSql().replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
            if (sql != null && (sql.toUpperCase().startsWith("INSERT") || sql.toUpperCase().startsWith("UPDATE") || sql.toUpperCase().startsWith("DELETE"))) {
            }
        }

        return invocation.proceed();
    }

    private String fetchSQLParam(MappedStatement ms, BoundSql boundSql) {

        StringBuffer sqlPara = new StringBuffer("");

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        Configuration configuration = ms.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();

        if (parameterMappings != null && !parameterMappings.isEmpty()) {
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }

                    sqlPara.append("[").append(propertyName).append("=").append(value).append("]");
                }
            }
        }

        return sqlPara.toString();
    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }

        return newBoundSql;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
                ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        String[] keyProperties = ms.getKeyProperties();
        builder.keyProperty(keyProperties == null ? null : keyProperties[0]);

        // setStatementTimeout()
        builder.timeout(ms.getTimeout());

        // setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        // setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        // setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 
     * @author mabi
     *
     */
    private static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    private String getPageSql(Page<?> page, String initSQL) {

        StringBuffer sb = new StringBuffer();
        sb.append(initSQL);
        sb.append(" LIMIT ").append(page.getLimit()).append(" OFFSET ").append(page.getOffset());

        return sb.toString();
    }

    private String getCountSql(String sql) {
        return "SELECT count(*) FROM ("
                + sql.trim().replaceAll(";$", "").replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ") + ") page_temp";
    }

    private int queryTotal(MappedStatement mappedStatement, BoundSql boundSql, String param) throws SQLException {

        Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;

        String sql = boundSql.getSql();

        try {

            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();

            String countSql = getCountSql(sql);
            
            System.out.println("============sql for count :" + countSql);

            BoundSql countBoundSql = copyFromBoundSql(mappedStatement, boundSql, countSql);
            

            ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,
                    countBoundSql.getParameterObject(), countBoundSql);

            countStmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(countStmt);

            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }

            return totalCount;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (countStmt != null) {
                try {
                    countStmt.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    private Page<?> findPageableObject(Object params) {

        if (params == null) {
            return null;
        }

        if (Page.class.isAssignableFrom(params.getClass())) {
            return (Page<?>) params;
        } else if (params instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> paramMap = (Map<String, Object>) params;
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                Object paramValue = entry.getValue();
                if (paramValue != null && Page.class.isAssignableFrom(paramValue.getClass())) {
                    return (Page<?>) paramValue;
                }
            }
        }

        return null;
    }

    @Override
    public Object plugin(Object target) {
    	System.out.println("==========================plugin=======");
        if (Executor.class.isAssignableFrom(target.getClass())) {
            return Plugin.wrap(target, this);
        }

        return target;
    }

    @Override
    public void setProperties(Properties arg0) {
    	System.out.println("==========================setProperties=======");
    }

}
