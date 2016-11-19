package com.twosnail.frame.commin.util.kit.string;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;
 
/**
 * 验证身份证号码.
 * 身份号码是特征组合码，由十七位数字本体码和一位校验码组成。
 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 身份证地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * 身份证出生日期码表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 身份证顺序码表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * 身份证校验码是十七位数字本体码加权求和而得出的。
 * 
 * 二、编码规则：公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位校验码，可以用字母表示如为ABCDEFYYYYMMDDXXXR。其含义如下：
 * 1. 地址码（ABCDEF）：表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
 * 2. 出生日期码（YYYYMMDD）：表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日分别用4位、2位（不足两位加0）、2位（不足两位加0）数字表示，之间不用分隔符。
 * 3. 顺序码（XXX）：表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * 4．校验码（R），一位数字，通过前17位数字根据一定计算得出，检验码分别是“0、1、2、……10”共11个数字，当检验码为“10”时，为了保证公民身份证号码18位，所以用“X”表示。
 * 
 * 1、关于中国居民身份证的常识：
 *  	我国现行使用公民身份证号码有两种尊循两个国家标准，〖GB 11643-1989〗和〖GB 11643-1999〗。
 *		〖GB 11643-1989〗中规定的是15位身份证号码：排列顺序从左至右依次为：六位数字地址码，六位数字出生日期码，三位数字顺序码，其中出生日期码不包含世纪数。
 *		〖GB 11643-1999〗中规定的是18位身份证号码：公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 *		地址码：表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
 *		出生日期码：表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
 *		顺序码：表示同一地址码所标识的区域范围内，对同年、同月、同日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
 *		校验码：是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。
 *
 *关于身份证号码最后一位的校验码的算法如下：
 *∑(a[i]*W[i]) mod 11 ( i = 2, 3, ..., 18 )
 *
 * 	"*" ： 表示乘号
 *	i：  表示身份证号码每一位的序号，从右至左，最左侧为18，最右侧为1。
 *　a[i]： 表示身份证号码第 i 位上的号码
 *　W[i]： 表示第 i 位上的权值 W[i] = 2^(i-1) mod 11
 *　设：R = ∑(a[i]*W[i]) mod 11 ( i = 2, 3, ..., 18 )
 *　C = 身份证号码的校验码
 *　则R和C之间的对应关系如下表：
 *　　R：0 1 2 3 4 5 6 7 8 9 10
 *　　C：1 0 X 9 8 7 6 5 4 3 2
 *　由此看出 X 就是 10，罗马数字中的 10 就是X，所以在新标准的身份证号码中可能含有非数字的字母X。
 */
public class IdentityKit {
	
//    位权值数组
    private static byte[] Wi=new byte[17];
//    身份证前部分字符数
    private static final byte fPart = 6;
//    身份证算法求模关键值
    private static final byte fMod = 11;
//    旧身份证长度
    private static final byte oldIDLen = 15;
//    新身份证长度
    private static final byte newIDLen = 18;
//    新身份证年份标志
    private static final String yearFlag = "19";
//    校验码串 
    private static final String CheckCode="10X98765432"; 
//    最小的行政区划码
    private static final int minCode = 150000;
//    最大的行政区划码
    private static final int maxCode = 700000;
//    旧身份证号码
//    private String oldIDCard="";
//    新身份证号码
//    private String newIDCard="";
//    地区及编码
    
    
    //private String Area[][2] = 
    private static void setWiBuffer(){
        for(int i=0;i<Wi.length;i++){    
            int k = (int) Math.pow(2, (Wi.length-i));
            Wi[i] = (byte)(k % fMod);
        }
    }
    
    //获取新身份证的最后一位:检验位
    private static String getCheckFlag(String idCard){
        int sum = 0;
        //进行加权求和 
        for(int i=0; i<17; i++){        
            sum += Integer.parseInt(idCard.substring(i,i+1)) * Wi[i];
        }
        //取模运算，得到模值
        byte iCode = (byte) (sum % fMod); 
        return CheckCode.substring(iCode,iCode+1);
    }
    
    //获取时间串
    private static String getIDDate(final String idCard,boolean newIDFlag){
        String dateStr = "";
        if(newIDFlag)
            dateStr = idCard.substring(fPart,fPart+8);
        else
            dateStr = yearFlag + idCard.substring(fPart,fPart+6);
        return dateStr;
    }
    
    //判断时间合法性
    private static boolean checkDate(final String dateSource){
        String dateStr = dateSource.substring(0,4)+"-"+dateSource.substring(4,6)+"-"+dateSource.substring(6,8);
        System.out.println(dateStr);
        DateFormat df = DateFormat.getDateInstance();
        df.setLenient(false);
        try {
            Date date= df.parse(dateStr);
            return (date!=null);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }
    
    //旧身份证转换成新身份证号码
    public static String getNewIDCard(final String oldIDCard){
        //初始化方法
        IdentityKit.setWiBuffer();
        if(!checkIDCard(oldIDCard)){
            return oldIDCard;
        }
        String newIDCard = oldIDCard.substring(0, fPart);
        newIDCard += yearFlag;
        newIDCard += oldIDCard.substring(fPart, oldIDCard.length());
        String ch = getCheckFlag(newIDCard);
        newIDCard += ch;
        return newIDCard;
    }
    
    //新身份证转换成旧身份证号码
    public static String getOldIDCard(final String newIDCard){
        //初始化方法
    	IdentityKit.setWiBuffer();
        if(!checkIDCard(newIDCard)){
            return newIDCard;
        }
        String oldIDCard = newIDCard.substring(0,fPart)+
                    newIDCard.substring(fPart+yearFlag.length(),newIDCard.length()-1);
        return oldIDCard;
    }
    
    //判断身份证号码的合法性
    public static boolean checkIDCard(final String idCard){
        //初始化方法
    	IdentityKit.setWiBuffer();
        boolean isNew = false;
        //String message = "";
        //检查身份证号码长度
        int length = idCard.length();
        if( length == newIDLen ) {
        	isNew = true;
        } else if( length == oldIDLen ) {
        	isNew = false;
        } else {
        	return false;
        }
        String idDate = getIDDate(idCard, isNew);
        if(!checkDate(idDate)){
            //message = "ID时间异常";
            return false;
        }
        if(isNew){
            String checkFlag = getCheckFlag(idCard);
            String theFlag = idCard.substring(idCard.length()-1,idCard.length());
            if(!checkFlag.equals(theFlag)){
                //message = "新身份证校验位异常";
                return false;
            }
        }
        return true;
    }
    
    //获取一个随机的"伪"身份证号码
    public static String getRandomIDCard(final boolean idNewID){
        //初始化方法
    	IdentityKit.setWiBuffer();
        Random ran = new Random();
        String idCard = getAddressCode(ran)+getRandomDate(ran,idNewID)+getIDOrder(ran);
        if(idNewID){
            String ch = getCheckFlag(idCard);
            idCard += ch;
        }
        return idCard;
    }
    
    //产生随机的地区编码
    private static String getAddressCode(Random ran) {
        if(ran==null){
            return "";
        }else{
            int addrCode = minCode + ran.nextInt(maxCode-minCode);
            return Integer.toString(addrCode);
        }
    }
    
    //产生随机的出生日期
    private static String getRandomDate(Random ran, boolean idNewID) {
        // TODO Auto-generated method stub
        if(ran==null){
            return "";
        }
        int year = 0;
        if(idNewID){
            year = 1900 + ran.nextInt(2007-1900);
        }else{
            year = 1 + ran.nextInt(99);
        }
        int month = 1+ran.nextInt(12);
        int day = 0;
        if(month==2){
            day= 1+ran.nextInt(28);
        }else if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
            day= 1+ran.nextInt(31);
        }else{
            day= 1+ran.nextInt(30);
        }
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);
        String dateStr = Integer.toString(year)+nf.format(month)+nf.format(day);
        return dateStr;
    }
 
    //产生随机的序列号
    private static String getIDOrder(Random ran) {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(3);
        nf.setMinimumIntegerDigits(3);
        if(ran==null){
            return "";
        }else{
            int order = 1+ran.nextInt(999);
            return nf.format(order);
        }
    }
 
    private IdentityKit(){
        setWiBuffer();
    }
    
}