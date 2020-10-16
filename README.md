# OwnProject_simpleShop
*Project purpose*

Implementation of basic internet shop functionality. It has 
- user authentication  
- user authorisation
- product set up (add and delete)
- add and remove products to shopping cart
- convert shopping cart into order

*Project structure*

Project has following levels: 
- DAO to connect with mySQL based on JDBC
- Controller - to work with web side through servlets 
and jsp files. This also includes Filters for Authorisation
 and Authentication
- Service - realisation of business logic

*Implementation details*

- Injector is used across all Dao and Service classes
- Dao has two implementations - internal storage (class Storage) 
and JDBC implementation of working with mySQL DB. 
To shift working with internal Storage, you need to set Annotation
@Dao for all DaoImpl classes and Storage and remove @Dao from
...DaoJdbcImpl classes;
- Security:  implementing hash  and salt (SHA-512 algorithm)
 to store passwords in DataBase ;
 - Roles: two roles (Admin and User) were defined with different 
 access rights.  
Admin (user with this role) is not able to create 
shopping carts and orders. But has exclusive rights to 
create and delete products, view all orders and users, 
delete order and user;  

*Launch guide*

- Install JDK 1.11, Maven, tomcat.
- Adjust settings to your DataBase in ConnectionUtil;
- Run script from init_db.sql file to 
set up your data base (schema and necessary tables). 
Also it will help to set up roles and first user with 
Admin role. You should adjust password for this user.

*Author*

Felix Yaroslavskiy
https://github.com/fyarkiy/OwnProject_simpleShop
