#Java SDK
# [Kavenegar RESTful API Document](http://kavenegar.com/rest.html)
If you need to future information about API document Please visit RESTful Document

## Installation
First of all, You need to make an account on Kavenegar from [Kaveneagr webpage](https://panel.kavenegar.com/Client/Membership/Register)


After that you just need to pick API-KEY up from [My Account](http://panel.kavenegar.com/Client/setting/index) section.
Here You can download the [Java SDK] (https://github.com/KaveNegar/kavenegar-java/archive/master.zip) or just pull it.
Anyway there is good tutorial about [Pull  request](http://gun.io/blog/how-to-github-fork-branch-and-pull-request/)


## Usage
Well,You can see an example of sending SMS through JAVA below . 

```java

try {
     KavenegarApi  api= new KavenegarApi("");
      SendResult Result = api.Send("SenderLine", "Your Receptor", "خدمات پیام کوتاه کاوه نگار");
}
catch (HttpException ex)
{ // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
  System.out.print("HttpException  : " + ex.getMessage());
}
catch (ApiException ex)
{ // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
   System.out.print("ApiException : " + ex.getMessage());
}

```

#Contribution
 Bug fixes, docs, and enhancements welcome!I know that there are so many people do these thingi you're one of the  just please let us know [support@kavenegar.com](mailto:support@kavenegar.com?Subject=SDK)
<p dir='rtl'>
##  راهنمای فارسی 

### راهنما

در صورتی که مایل هستید راهنمای فارسی کیت توسعه کاوه نگار را مطالعه کنید به صفحه
[کد ارسال پیامک](http://kavenegar.com/sdk.html) 
مراجعه کنید.


### مستندات


برای مطالعه مستندات کار با
[ وب سرویس اس ام اس](http://kavenegar.com)
کاوه نگار به صفحه [مستندات](http://kavenegar.com/rest.html)مراجعه کنید

### معرفی وب سرویس کاوه نگار

برای مشاهده ویژگی های وب سرویس پیامک کاوه نگار به [صفحه  وب سرویس](http://kavenegar.com/%D9%88%D8%A8%D8%B3%D8%B1%D9%88%DB%8C%D8%B3-%D9%BE%DB%8C%D8%A7%D9%85%DA%A9.html)مراجعه نمائید.

### ایجاد حساب کاربری

و بالاخره اگر در استفاده از سرویس کاوه نگار مشکلی داشتید یا پیشنهاد همکاری  بود لطفا حتما به ما اطلاع دهید.

[support@kavenegar.com](mailto:support@kavenegar.com)
</p>
