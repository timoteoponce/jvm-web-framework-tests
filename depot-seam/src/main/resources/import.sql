-- You can use this file to load seed data into the database using SQL statements
insert into Member (id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212') ;
insert into Product(id,version,title,description,image_url,price) values (0,'0','Book 1','Book description 1','/resources/images/books/debug.jpg',150);
insert into Product(id,version,title,description,image_url,price) values (-1,'0','Book 2','Book description 2','/resources/images/books/rails.png',210);
insert into Product(id,version,title,description,image_url,price) values (-2,'0','Book 3','Book description 3','/resources/images/books/rtp.jpg',99);
insert into Product(id,version,title,description,image_url,price) values (-3,'0','Book 4','Book description 4','/resources/images/books/ruby.jpg',299.99);