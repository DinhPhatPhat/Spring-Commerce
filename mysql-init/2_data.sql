insert into springcommerce_db.users(name, phone_number, email, password, role, is_active) values
(
	"Admin",
    "0868063569",
    "dinhphatphat.work@gmail.com",
    "Phat@123#",
    1,
    1
);

insert into springcommerce_db.users(name, phone_number, email, password, role, is_active) values
(
        "User",
        "0868063568",
        "dinhtainang@gmail.com",
        "Phat@123#",
        0,
        1
);
SHOW CREATE TABLE springcommerce_db.categories;
-- INSERT INTO categories
INSERT INTO springcommerce_db.categories (name) VALUES
                                  ('Chậu'),
                                  ('Cây trong nhà'),
                                  ('Cây ngoài trời'),
                                  ('Cây dịp Tết');

-- INSERT INTO products (bỏ image_path)
INSERT INTO springcommerce_db.products (name, description, price, category_id, brand, color, is_active)
VALUES
-- Chậu
('Chậu đất nung tròn', 'Chậu gốm sứ truyền thống, thích hợp trồng sen đá.', 50000, 1, N'Gốm Bát Tràng', 'Nâu đất', 1),
('Chậu treo ban công', 'Chậu nhựa dẻo nhẹ, dễ treo ở ban công.', 35000, 1, 'GardenPro', 'Trắng', 1),

-- Cây trong nhà
('Cây lưỡi hổ', 'Cây giúp lọc không khí rất tốt, phù hợp để bàn làm việc.', 80000, 2, 'GreenHome', 'Xanh', 1),
('Cây kim tiền', 'Loại cây phong thủy hút tài lộc, thường đặt trong nhà.', 120000, 2, 'Phúc Lộc', 'Xanh', 1),

-- Cây ngoài trời
('Cây si rô', 'Cây ăn trái dễ trồng, thích hợp ngoài vườn.', 90000, 3, 'Vườn Xanh', 'Xanh', 1),
('Cây hoa giấy', 'Cây cảnh leo giàn, hoa nở quanh năm.', 75000, 3, 'Nhà Vườn An Phát', 'Tím', 1),

-- Cây dịp Tết
('Cây quất cảnh', 'Cây truyền thống ngày Tết, tượng trưng cho sung túc.', 200000, 4, 'Tứ Quý Garden', 'Cam', 1),
('Cây mai vàng', 'Loài cây đặc trưng của miền Nam trong dịp Tết.', 500000, 4, 'Mai Vàng Sài Gòn', 'Vàng', 1);
