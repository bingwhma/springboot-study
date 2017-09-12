Google 的 protobuf 在业界非常流行,很多商业项目选择 protobuf 作为编码解码框架,这里我们一起回顾一下 Protobuf 的优点.
在 Google 内长期使用,产品成熟度高;
跨语言,支持多种语言,包括 c++,Java, 和 Python;
编码后的消息更小,更加有利于存储和传输;
编码的性能非常高;
支持不同协议版本的向前兼容;
支持定义可选和必选字段;





.proto类型	Java 类型	C++类型	备注
double	double	double	
float	float	float	
int32	int	int32	使用可变长编码方式。编码负数时不够高效——如果你的字段可能含有负数，那么请使用sint32。
int64	long	int64	使用可变长编码方式。编码负数时不够高效——如果你的字段可能含有负数，那么请使用sint64。
uint32	int[1]	uint32	Uses variable-length encoding.
uint64	long[1]	uint64	Uses variable-length encoding.
sint32	int	int32	使用可变长编码方式。有符号的整型值。编码时比通常的int32高效。
sint64	long	int64	使用可变长编码方式。有符号的整型值。编码时比通常的int64高效。
fixed32	int[1]	uint32	总是4个字节。如果数值总是比总是比228大的话，这个类型会比uint32高效。
fixed64	long[1]	uint64	总是8个字节。如果数值总是比总是比256大的话，这个类型会比uint64高效。
sfixed32	int	int32	总是4个字节。
sfixed64	long	int64	总是8个字节。
bool	boolean	bool	
string	String	string	一个字符串必须是UTF-8编码或者7-bit ASCII编码的文本。
bytes	ByteString	string	可能包含任意顺序的字节数据。