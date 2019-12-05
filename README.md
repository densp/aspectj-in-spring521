## aspectj-in-spring521 - Reproduce probelm with AspectJ Load Time Weaving on Spring 5.2.1
This web application allows reproducing problem with AspectJ Load Time Weaving in Spring 5.2.1 (compared with the behavior on Spring 5.1.11).

### Application description
We have Spring (5.1.11) web application, XML based application context. Project is rather large, we heavily use both component scan and xml description to define beans.
There are several aspects which provide around advices for the annotated methods. We use AspectJ definition and Load Time Weaving. 
Application server - Caucho Resin 4.0.62 (although identical problem and behavior was reproduced on Tomcat 9.0.20).
We did **NOT** use "-javaagent" to apply neither aspectjweaver-1.9.4.jar not spring-instrument-5.1.11.RELEASE.jar.
Aspects were applied without any problems.

Application in the current repository is minimal variant which demostrates Aspects behavior in Spring 5.1.11 and 5.2.1.
Tip: one can switch parent pom between

            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId> 
            <version>2.1.10.RELEASE</version>

and 
    
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId> 
            <version>2.2.1.RELEASE</version>

to reproduce behaviour.

'DSAspect' - aspect which introduces AroundAdvice for annotated methods, on method-execution.

'DSAAspectAnnotation' - annotation to mark methods to be woven.

package 'com.ds.aspectjspring521.beans' - defines multiple Beans. Each of beans contain two methods - one marked with anotation another not (just to verify that aspect is woven properly).

There are multiples variants of beans (important due to the detected behaviour):
* with or without interface
* defined in XML / covered by autoscan / defined in annotation config
* not beans at all

Building web app:

            mvn package

Maven builds *aspectj-in-spring521.war* for you
This web application was launched under tomcat (placed to webapps) and caucho resin (config example is available in resin folder).

http://localhost:8080/api/test - shows how each kind of bean was executed. 

Actually we've got rather strange results... (and this is the reason why there are some many different kinds of beans in the application)

### Execution results on Spring 5.1.11 + Resin/Tomcat + NO -javaagent
            autoscan, no interface, aspect: AroundAdvice >>>>BeanFromAutoscanNoInterface -> doAspect worked<<<< AroundAdvice
            autoscan, no interface, no aspect: BeanFromAutoscanNoInterface -> doNoAspect worked
            
            autoscan, with interface, aspect: AroundAdvice >>>>BeanFromAutoscanWithInterface -> doAspect worked<<<< AroundAdvice
            autoscan, with interface, no aspect: BeanFromAutoscanWithInterface -> doNoAspect worked
            
            
            xml, no interface, aspect: AroundAdvice >>>>BeanFromXmlConfigNoInterface -> doAspect worked<<<< AroundAdvice
            xml, no interface, no aspect: BeanFromXmlConfigNoInterface -> doNoAspect worked
            
            xml, with interface, aspect: AroundAdvice >>>>BeanFromXmlConfigWithInterface -> doAspect worked<<<< AroundAdvice
            xml, with interface, no aspect: BeanFromXmlConfigWithInterface -> doNoAspect worked
            
            
            annotation config, no interface, aspect: BeanViaAnnotationConfigNoInterface -> doAspect worked
            annotation config, no interface, no aspect: BeanViaAnnotationConfigNoInterface -> doNoAspect worked
            
            annotation config, with interface, aspect: AroundAdvice >>>>BeanViaAnnotationConfigWithInterface -> doAspect worked<<<< AroundAdvice
            annotation config, with interface, no aspect: BeanViaAnnotationConfigWithInterface -> doNoAspect worked
            
            
            not a bean, no interface, aspect: AroundAdvice >>>>NotABean -> doAspect worked<<<< AroundAdvice
            not a bean, no interface, no aspect: NotABean -> doNoAspect worked

Result is actually **almost** fine. Beans defined with and without interfaces via XML or autoscan have been woven properly.
What is strange to me - annotation configured beans **without** interface are **not** woven! 
It looks as if during context initialisation spring lookups corresponding class BEFORE classloader transformation in case we use Spring fallback scenario. 
And finally such "preloaded" classes cannot be properly woven.
In case a bean has an interface - only interface will be "preloaded" and the implementing class later on will be properly woven.
Luckily we did not use annotation configured beans and thus did not notice such behavior.

***Open point - is it correct behavior for aspects weaving?*** 

### Execution results on Spring 5.1.11 + Resin/Tomcat + with -javaagent (both aspectjweaver and spring-instrument.jar)
            autoscan, no interface, aspect: AroundAdvice >>>>BeanFromAutoscanNoInterface -> doAspect worked<<<< AroundAdvice
            autoscan, no interface, no aspect: BeanFromAutoscanNoInterface -> doNoAspect worked
            
            autoscan, with interface, aspect: AroundAdvice >>>>BeanFromAutoscanWithInterface -> doAspect worked<<<< AroundAdvice
            autoscan, with interface, no aspect: BeanFromAutoscanWithInterface -> doNoAspect worked
            
            
            xml, no interface, aspect: AroundAdvice >>>>BeanFromXmlConfigNoInterface -> doAspect worked<<<< AroundAdvice
            xml, no interface, no aspect: BeanFromXmlConfigNoInterface -> doNoAspect worked
            
            xml, with interface, aspect: AroundAdvice >>>>BeanFromXmlConfigWithInterface -> doAspect worked<<<< AroundAdvice
            xml, with interface, no aspect: BeanFromXmlConfigWithInterface -> doNoAspect worked
            
            
            annotation config, no interface, aspect: AroundAdvice >>>>BeanViaAnnotationConfigNoInterface -> doAspect worked<<<< AroundAdvice
            annotation config, no interface, no aspect: BeanViaAnnotationConfigNoInterface -> doNoAspect worked
            
            annotation config, with interface, aspect: AroundAdvice >>>>BeanViaAnnotationConfigWithInterface -> doAspect worked<<<< AroundAdvice
            annotation config, with interface, no aspect: BeanViaAnnotationConfigWithInterface -> doNoAspect worked
            
            
            not a bean, no interface, aspect: AroundAdvice >>>>NotABean -> doAspect worked<<<< AroundAdvice
            not a bean, no interface, no aspect: NotABean -> doNoAspect worked

We tried to apply javaagent and here everything works as expected. 
Classloader is prepared in advance (before Spring context loading) and thus all aspects could be applied properly.
No matter how bean was defined - aspect is successfully woven. 

### Execution results on Spring 5.2.11 + Resin/Tomcat + NO -javaagent 
Having migrated to Spring 5.2.1 (5.2.2 has been checked as well) we noticed that our Aspect are NOT woven anymore and started detailed investigations.
Here is the result we received:

                autoscan, no interface, aspect: BeanFromAutoscanNoInterface -> doAspect worked
                autoscan, no interface, no aspect: BeanFromAutoscanNoInterface -> doNoAspect worked
                
                autoscan, with interface, aspect: BeanFromAutoscanWithInterface -> doAspect worked
                autoscan, with interface, no aspect: BeanFromAutoscanWithInterface -> doNoAspect worked
                
                
                xml, no interface, aspect: AroundAdvice >>>>BeanFromXmlConfigNoInterface -> doAspect worked<<<< AroundAdvice
                xml, no interface, no aspect: BeanFromXmlConfigNoInterface -> doNoAspect worked
                
                xml, with interface, aspect: AroundAdvice >>>>BeanFromXmlConfigWithInterface -> doAspect worked<<<< AroundAdvice
                xml, with interface, no aspect: BeanFromXmlConfigWithInterface -> doNoAspect worked
                
                
                annotation config, no interface, aspect: BeanViaAnnotationConfigNoInterface -> doAspect worked
                annotation config, no interface, no aspect: BeanViaAnnotationConfigNoInterface -> doNoAspect worked
                
                annotation config, with interface, aspect: AroundAdvice >>>>BeanViaAnnotationConfigWithInterface -> doAspect worked<<<< AroundAdvice
                annotation config, with interface, no aspect: BeanViaAnnotationConfigWithInterface -> doNoAspect worked
                
                
                not a bean, no interface, aspect: AroundAdvice >>>>NotABean -> doAspect worked<<<< AroundAdvice
                not a bean, no interface, no aspect: NotABean -> doNoAspect worked

Behavior (as well as na open question) with annotation configured beans without interfaces still remain.
 
***BUT*** all autoscan beans (with and without interfaces) are not woven anymore!

This is rather big change. It looks as if lookup of beans definition causes their loading by classloader BEFORE transformer is applied to the classloader (in this scenario load time weaving is defined via spring context).
So all autoscan defined beans will not be woven if we use fallback spring load time weaver.

####The question is if this is intended behavior or a bug?
By the way - Spring web app got same behavior as Spring Boot application where external javaagent is required to properly apply AspectJ load time weaving. 

FYI: variant with externally defined javaagents doeas work in Spring 5.2.1 (as expected).
