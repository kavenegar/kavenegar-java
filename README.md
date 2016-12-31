#Java SDK
# [Kavenegar RESTful API Document](http://kavenegar.com/rest.html)
If you need to future information about API document Please visit RESTful Document

## Installation
First of all, You need to make an account on Kavenegar from [Kaveneagr webpage](https://panel.kavenegar.com/Client/Membership/Register)


After that you just need to pick API-KEY up from [My Account](http://panel.kavenegar.com/Client/setting/index) section.
Here You can download the [Java SDK] (https://github.com/KaveNegar/kavenegar-java/archive/master.zip) or just pull it.
Anyway there is good tutorial about [Pull  request](http://gun.io/blog/how-to-github-fork-branch-and-pull-request/)

### To get a Git project into your build:

Add it depent on what you are using .

#### gradle
 Step 1 <p> Add the JitPack repository to your build file </p>
 
```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2<p>Add the dependency</p>

```java
dependencies {
		compile 'com.github.User:Repo:Tag'
	}
```
#### maven

 Step 1 <p> Add the JitPack repository to your build file </p>
 
```java
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
Step 2<p>Add the dependency</p>

```java
<dependency>
	    <groupId>com.github.User</groupId>
	    <artifactId>Repo</artifactId>
	    <version>Tag</version>
	</dependency>
```
####  sbt

Step 1 <p> Add the JitPack repository to your build file </p>
 
```java
    resolvers += "jitpack" at "https://jitpack.io"

```
Step 2<p>Add the dependency</p>

```java
    libraryDependencies += "com.github.User" % "Repo" % "Tag"

```
#### leiningen

Step 1 <p> Add the JitPack repository to your build file </p>
 
```java
    :repositories [["jitpack" "https://jitpack.io"]]

	}
```
Step 2<p>Add the dependency</p>

```java
    :dependencies [[com.github.User/Repo "Tag"]]

```
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
 Bug fixes, docs, and enhancements welcome!I know that there are so many people do these thingi you're one of the  just please let us know [support@kavenegar.com](mailto:support@kavenegar.com?Subject=SDK
 <hr>
<div dir='rtl'>

<h4 id="">راهنمای فارسی</h4>
<h5 id="-1">راهنما</h5>
<p>در صورتی که مایل هستید راهنمای فارسی کیت توسعه کاوه نگار را مطالعه کنید به صفحه
<a href="http://kavenegar.com/sdk.html">کد ارسال پیامک</a> 
مراجعه کنید.</p>
<h5 id="-2">مستندات</h5>
<p>برای مطالعه مستندات کار با
<a href="http://kavenegar.com"> وب سرویس اس ام اس</a>
کاوه نگار به صفحه <a href="http://kavenegar.com/rest.html">مستندات</a>مراجعه کنید</p>
<h5 id="-3">معرفی وب سرویس کاوه نگار</h5>
<p>برای مشاهده ویژگی های وب سرویس پیامک کاوه نگار به <a href="http://kavenegar.com/%D9%88%D8%A8%D8%B3%D8%B1%D9%88%DB%8C%D8%B3-%D9%BE%DB%8C%D8%A7%D9%85%DA%A9.html">صفحه  وب سرویس</a>مراجعه نمائید.</p>
<h5 id="-4">ایجاد حساب کاربری</h5>
<p>و بالاخره اگر در استفاده از سرویس کاوه نگار مشکلی داشتید یا پیشنهاد همکاری  بود لطفا حتما به ما اطلاع دهید.</p>
<p><a href="mailto:support@kavenegar.com">support@kavenegar.com</a></p>
</div>
