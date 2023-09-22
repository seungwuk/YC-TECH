# RDBMS(관계형 데이터베이스 관리 시스템) -테이블들의 연관 관계       
## unique key - table column의 uniqueness를 지켜줌
        
    기능 : null value 하나 수용가능, duplicate value 수용 불가, 여러개 unique key 가능
        
```sql
CREATE TABLE table_name(column1 datatype UNIQUE KEY, column2 datatype UNIQUE KEY, columnN datatype);

//example of multiple unique keys
        
1. 이미 존재하는 column에 unique key 부여하기
ALTER TABLE CUSTOMERS ADD CONSTRAINT UNIQUE_ADDRESS UNIQUE(ADDRESS);

// customers = table name ,unique_address - column name
        
2. unique key 제거하기
ALTER TABLE CUSTOMERS DROP CONSTRAINT UNIQUE_ADDRESS;
```
        
        
## primary key - uniquely indentify, 데이터 접근을 빠르게 해주고 relationship을 만듦.
        
    특징 : contains only a unique value, null 은 안됨, one table에는 one primary key, 900bytes보다 짧아야함
        
ex) 고객 정보에서 cust_id가 primary key 역할을 하며 다른 것들과 구분해준다.
        
```sql
CREATE TABLE CUSTOMERS (ID INT NOT NULL, NAME VARCHAR (20) NOT NULL, AGE INT NOT NULL, ADDRESS CHAR (25), SALARY DECIMAL (18, 2), PRIMARY KEY (ID));
// id는 똑같은 값이 중복되면 안된다, null도 안되고
        
// 1. 이미 존재하는 column에 primary key 부여
ALTER TABLE CUSTOMERS ADD CONSTRAINT PRIMARY KEY(NAME);
        
// 2. primary key drop
ALTER TABLE CUSTOMERS DROP PRIMARY KEY;
```
        

## foreign key - column in one table that matches a primary key in another table(column하나만으로 대표성을 띄기 어려우므로 보충해주는 역할)
    
    특징 : redundancy를 줄여줌, normalize 하는 데 도움
    
```sql
    CREATE TABLE ORDERS (
       ID INT NOT NULL,
       DATE DATETIME, 
       CUSTOMER_ID INT,
       CONSTRAINT FK_CUSTOMER 
       FOREIGN KEY(CUSTOMER_ID) 
       REFERENCES CUSTOMERS(ID),
       AMOUNT DECIMAL,
       PRIMARY KEY (ID)
    );
    
    // 1. 이미 존재하는 column에 foreign key constraint
    ALTER TABLE ORDERS 
    ADD CONSTRAINT FK_ORDERS 
    FOREIGN KEY(ID) 
    REFERENCES CUSTOMERS(ID);
    // 2. dropping a foreign key
    ALTER TABLE ORDERS DROP FOREIGN KEY FK_ORDERS;
    
```
    
    | primary key | foreign key |
    | --- | --- |
    | unique | can be duplicated |
    | null x | can be null |
    | only one per table | more than one per table |
    - 
    
## indexing
  
    SQL Index는 special lookup table, data pointer를 가지고 있어서 찾기 쉽다(= book index)
    
```sql
    CREATE INDEX index_name ON table_name;
    
    //1. unique index : data duplicate를 막아준다.(primary, unique constraint 사용됨)
    CREATE UNIQUE INDEX index_name
    on table_name (column_name);
    
    //2. Single-column index : only on one column
    CREATE INDEX index_name
    ON table_name (column_name);
    
    //3. Composite Indexes : two or more columns
    CREATE INDEX index_name
    on table_name (column1, column2);
    
    //4. Implicit Indexes : object가 만들어질 때 자동으로 생성.
    
    // Index statement drop
    DROP INDEX index_name;
```
    
  Index 사용을 고려해 보아야하는 경우:
    
    - small tables
    - frequent, large batch updates, insert가 일어나는 table
    - high number of null value가 있는 table
    - 빈번하게 바뀌는 column이 있는 table
    
    
    
## SQL- Create Index
    
```sql
    CREATE INDEX index_name 
    ON table_name (column_name1, column_name2,... column_nameN);
    
    //example
    CREATE TABLE CUSTOMERS(
       ID INT NOT NULL,
       NAME VARCHAR(15) NOT NULL,
       AGE INT NOT NULL,
       ADDRESS VARCHAR(25),
       SALARY DECIMAL(10, 4),
       PRIMARY KEY(ID));
    );
    
    //create index name
    CREATE INDEX index_name ON CUSTOMERS(NAME);
    CREATE INDEX mult_index_data on CUSTOMERS(NAME, AGE);
    
    //result
    SHOW INDEX FROM CUSTOMERS;
    
    //Table	 Non_unique	Key_name	  Seq_in_index	Column_name
    //customers	0	      PRIMARY	    1	            ID
    //customers	1	      index_name	1	          NAME
    //customers	1	      mult_index_data	1	      NAME
    //customers	1	      mult_index_data	2	      AGE
```
    
    
    
## SQL- Drop Index
    
```sql
    //위 코드의 example 연속
    DROP INDEX index_name ON customers;
    SHOW INDEX FROM CUSTOMERS;
    Table	Non_unique	Key_name	Seq_in_index	Column_name
    customers	0	      PRIMARY	  1	            ID
    
    //DROP INDEX with IF EXISTS : 존재하지 않는 index를 삭제해도 에러 안뜸
    DROP INDEX IF EXISTS INDEX_NAME ON CUSTOMERS;
    
    ALTER TABLE customers
    DROP CONSTRAINT PK__CUSTOMER__3214EC27CB063BB7;
    //이런 식으로 primary key도 삭제 가능하다 (pk_customer_32~~~가 primary key)
```
    
    
    
## SQL- Show Index
    
```sql
    CREATE TABLE CUSTOMERS (
       ID INT NOT NULL,
       NAME VARCHAR (20) NOT NULL,
       AGE INT NOT NULL,
       ADDRESS CHAR (25),
       SALARY DECIMAL (20, 2),       
       PRIMARY KEY (ID)
     );
    CREATE INDEX INDEX_NAME ON CUSTOMERS(NAME);
    
    //show index
    SHOW INDEX FROM CUSTOMERS;
    //
    sp_helpindex [ @objname = ] 'name'
    // 정의된 index의 detailed 정보를 찾을 때 씀(name, type, column)
    //index_name, index_description, index_keys를 알려줌
    EXEC sys.sp_helpindex @objname = N'CUSTOMERS';

```
    
    
    
## SQL- Unique Index
    
    indexed column에서 2개의 row가 같은 값을 가지지 못하도록 하는 역할
    
```sql
    CREATE UNIQUE INDEX UNIQUE_ID ON CUSTOMERS (NAME, ADRESS);
    //이미 중복된 값이 있는 column에는 unique선언이 안됩니다.
    //unique column에 중복되는 값을 추가하려고 하면 error가 뜸
    // null이 중복되도 안됨
    //multiple column에서는 2개의 조합이 중복안되면 괜찮음
```
    
    
    
## transaction
    
여러개의 명령을 하나로 처리하는 방법
    
    properties
    
    1. Atomicity : 모두 반영되거나 하나도 반영이 안되거나
    2. Consistency : 일관성
    3. Isolation : 두 개가 같이 실행될 때 다른 하나에 끼어들 수 없음
    4. Durability : 결과는 영구적으로 반영되어야함

Transaction control

1. Commit - to save chages
2. Rollback - to roll back the changes
3. Savepoint - roll back 할 point 만들기
4. Set transaction - transaction에 이름 만들기

```sql
//1
SQL> DELETE FROM CUSTOMERS
   WHERE AGE = 25;
SQL> COMMIT;
//2
SQL> DELETE FROM CUSTOMERS
   WHERE AGE = 25;
SQL> ROLLBACK;
//3
SQL> SAVEPOINT SP1;
Savepoint created.
SQL> ROLLBACK TO SP1;
Rollback complete.
```


# Spring Data JPA 활용

JPA : Java의 ORM(Object Relational Mapping)기술에 대한 API 표준 명세 // 인터페이스의 모음.

ORM: 객체와 관계형 데이터 베이스를 매핑하는 기술

HIBERNATE : JPA의 구현체

SPRING DATA JPA: JPA를 사용하기 편하도록 만들어 놓은 모듈

## Reference
https://www.tutorialspoint.com/sql/sql-unique-key.htm
https://www.tutorialspoint.com/sql/sql-primary-key.htm
https://www.tutorialspoint.com/sql/sql-foreign-key.htm
https://www.tutorialspoint.com/sql/sql-indexes.htm
https://www.tutorialspoint.com/sql/sql-create-index.htm
https://www.tutorialspoint.com/sql/sql-drop-index.htm
https://www.tutorialspoint.com/sql/sql-show-indexes.htm
https://www.tutorialspoint.com/sql/sql-unique-index.h](https://www.tutorialspoint.com/sql/sql-unique-index.htm
https://www.tutorialspoint.com/sql/sql-transactions.htm
https://www.baeldung.com/learn-jpa-hibernate
