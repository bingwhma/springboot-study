package com.linyi.test;

import com.google.protobuf.InvalidProtocolBufferException;

public class Main {

	
    private static byte[] encode(PersonProto.Person user) {
        return user.toByteArray();
    }

    private static PersonProto.Person decode(byte[] body)
            throws InvalidProtocolBufferException {
        return PersonProto.Person.parseFrom(body);
    }
    
	public static void main(String[] args) throws Exception {

		
		PersonProto.Person.Builder builder = PersonProto.Person.newBuilder();
		builder.setId(1);
		builder.setName("xxxx");
		builder.setEmail("xxxxxx");
		PersonProto.Person user = builder.build();

		long start = System.currentTimeMillis();
        PersonProto.Person user2 = decode(encode(user));
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        System.out.println("After decode : " + user2.toString());
        System.out.println("Assert equal : --> " + user2.equals(user));
	        

	}

}
