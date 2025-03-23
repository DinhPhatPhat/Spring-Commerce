insert into peacestay.homes values 
(
	"Peacestay Thanh Bình",
    "Nguyễn Du",
    "0868063567",
    "Test home description"
    "..."
)

insert into ourstories.users(name, phone_number, email, password) values
(
	"Phat Phat",
    "0868063569",
    "dinhtainang@gmail.com",
    "phat123"
);

select * from ourstories.users u 

select * from ourstories.tokens

delete from ourstories.users u where email = '52200010@student.tdtu.edu.vn'

insert into ourstories.tokens (token, user_id, `type`) values
(
	'ssssssssssssssssssssssssssssssss',
	1,
	0
);