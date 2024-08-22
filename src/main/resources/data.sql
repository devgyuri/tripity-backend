-- article
INSERT INTO article (title, content, created_at, updated_at) VALUES ("제목1", "내용1", NOW(), NOW());
INSERT INTO article (title, content, created_at, updated_at) VALUES ("제목2", "내용2", NOW(), NOW());
INSERT INTO article (title, content, created_at, updated_at) VALUES ("제목3", "내용3", NOW(), NOW());


-- region
INSERT INTO region (name, code) VALUES ('서울', 1);
INSERT INTO region (name, code) VALUES ('인천', 2);
INSERT INTO region (name, code) VALUES ('대전', 3);
INSERT INTO region (name, code) VALUES ('대구', 4);
INSERT INTO region (name, code) VALUES ('광주', 5);
INSERT INTO region (name, code) VALUES ('부산', 6);
INSERT INTO region (name, code) VALUES ('울산', 7);
INSERT INTO region (name, code) VALUES ('세종', 8);
INSERT INTO region (name, code) VALUES ('경기', 9);
INSERT INTO region (name, code) VALUES ('강원', 10);
INSERT INTO region (name, code) VALUES ('충북', 11);
INSERT INTO region (name, code) VALUES ('충남', 12);
INSERT INTO region (name, code) VALUES ('경북', 13);
INSERT INTO region (name, code) VALUES ('경남', 14);
INSERT INTO region (name, code) VALUES ('전북', 15);
INSERT INTO region (name, code) VALUES ('전남', 16);
INSERT INTO region (name, code) VALUES ('제주', 17);


-- city
INSERT INTO city (name, code, region_id) VALUES ('강남구', 1, 1);
INSERT INTO city (name, code, region_id) VALUES ('강동구', 2, 1);
INSERT INTO city (name, code, region_id) VALUES ('강북구', 3, 1);
INSERT INTO city (name, code, region_id) VALUES ('강서구', 4, 1);
INSERT INTO city (name, code, region_id) VALUES ('관악구', 5, 1);
INSERT INTO city (name, code, region_id) VALUES ('광진구', 6, 1);
INSERT INTO city (name, code, region_id) VALUES ('구로구', 7, 1);
INSERT INTO city (name, code, region_id) VALUES ('금천구', 8, 1);
INSERT INTO city (name, code, region_id) VALUES ('노원구', 9, 1);
INSERT INTO city (name, code, region_id) VALUES ('도봉구', 10, 1);
INSERT INTO city (name, code, region_id) VALUES ('동대문구', 11, 1);
INSERT INTO city (name, code, region_id) VALUES ('동작구', 12, 1);
INSERT INTO city (name, code, region_id) VALUES ('마포구', 13, 1);
INSERT INTO city (name, code, region_id) VALUES ('서대문구', 14, 1);
INSERT INTO city (name, code, region_id) VALUES ('서초구', 15, 1);
INSERT INTO city (name, code, region_id) VALUES ('성동구', 16, 1);
INSERT INTO city (name, code, region_id) VALUES ('성북구', 17, 1);
INSERT INTO city (name, code, region_id) VALUES ('송파구', 18, 1);
INSERT INTO city (name, code, region_id) VALUES ('양천구', 19, 1);
INSERT INTO city (name, code, region_id) VALUES ('영등포구', 20, 1);
INSERT INTO city (name, code, region_id) VALUES ('용산구', 21, 1);
INSERT INTO city (name, code, region_id) VALUES ('은평구', 22, 1);
INSERT INTO city (name, code, region_id) VALUES ('종로구', 23, 1);
INSERT INTO city (name, code, region_id) VALUES ('중구', 24, 1);
INSERT INTO city (name, code, region_id) VALUES ('중랑구', 25, 1);
INSERT INTO city (name, code, region_id) VALUES ('도봉구', 10, 1);

INSERT INTO city (name, code, region_id) VALUES ('강화군', 1, 2);
INSERT INTO city (name, code, region_id) VALUES ('계양구', 2, 2);
INSERT INTO city (name, code, region_id) VALUES ('미추홀구', 3, 2);
INSERT INTO city (name, code, region_id) VALUES ('남동구', 4, 2);
INSERT INTO city (name, code, region_id) VALUES ('동구', 5, 2);
INSERT INTO city (name, code, region_id) VALUES ('부평구', 6, 2);
INSERT INTO city (name, code, region_id) VALUES ('서구', 7, 2);
INSERT INTO city (name, code, region_id) VALUES ('연수구', 8, 2);
INSERT INTO city (name, code, region_id) VALUES ('옹진군', 9, 2);
INSERT INTO city (name, code, region_id) VALUES ('중구', 10, 2);

INSERT INTO city (name, code, region_id) VALUES ('대덕구', 1, 3);
INSERT INTO city (name, code, region_id) VALUES ('동구', 2, 3);
INSERT INTO city (name, code, region_id) VALUES ('서구', 3, 3);
INSERT INTO city (name, code, region_id) VALUES ('유성구', 4, 3);
INSERT INTO city (name, code, region_id) VALUES ('중구', 5, 3);

INSERT INTO city (name, code, region_id) VALUES ('남구', 1, 4);
INSERT INTO city (name, code, region_id) VALUES ('달서구', 2, 4);
INSERT INTO city (name, code, region_id) VALUES ('달성군', 3, 4);
INSERT INTO city (name, code, region_id) VALUES ('동구', 4, 4);
INSERT INTO city (name, code, region_id) VALUES ('북구', 5, 4);
INSERT INTO city (name, code, region_id) VALUES ('서구', 6, 4);
INSERT INTO city (name, code, region_id) VALUES ('수성구', 7, 4);
INSERT INTO city (name, code, region_id) VALUES ('중구', 8, 4);
INSERT INTO city (name, code, region_id) VALUES ('군위군', 9, 4);

INSERT INTO city (name, code, region_id) VALUES ('광산구', 1, 5);
INSERT INTO city (name, code, region_id) VALUES ('남구', 2, 5);
INSERT INTO city (name, code, region_id) VALUES ('동구', 3, 5);
INSERT INTO city (name, code, region_id) VALUES ('북구', 4, 5);
INSERT INTO city (name, code, region_id) VALUES ('중구', 5, 5);

INSERT INTO city (name, code, region_id) VALUES ('강서구', 1, 6);
INSERT INTO city (name, code, region_id) VALUES ('금정구', 2, 6);
INSERT INTO city (name, code, region_id) VALUES ('기장군', 3, 6);
INSERT INTO city (name, code, region_id) VALUES ('남구', 4, 6);
INSERT INTO city (name, code, region_id) VALUES ('동구', 5, 6);
INSERT INTO city (name, code, region_id) VALUES ('동래구', 6, 6);
INSERT INTO city (name, code, region_id) VALUES ('부산진구', 7, 6);
INSERT INTO city (name, code, region_id) VALUES ('북구', 8, 6);
INSERT INTO city (name, code, region_id) VALUES ('사상구', 9, 6);
INSERT INTO city (name, code, region_id) VALUES ('사하구', 10, 6);
INSERT INTO city (name, code, region_id) VALUES ('서구', 11, 6);
INSERT INTO city (name, code, region_id) VALUES ('수영구', 12, 6);
INSERT INTO city (name, code, region_id) VALUES ('연제구', 13, 6);
INSERT INTO city (name, code, region_id) VALUES ('영도구', 14, 6);
INSERT INTO city (name, code, region_id) VALUES ('중구', 15, 6);
INSERT INTO city (name, code, region_id) VALUES ('해운대구', 16, 6);

INSERT INTO city (name, code, region_id) VALUES ('중구', 1, 7);
INSERT INTO city (name, code, region_id) VALUES ('남구', 2, 7);
INSERT INTO city (name, code, region_id) VALUES ('동구', 3, 7);
INSERT INTO city (name, code, region_id) VALUES ('북구', 4, 7);
INSERT INTO city (name, code, region_id) VALUES ('울주군', 5, 7);

INSERT INTO city (name, code, region_id) VALUES ('세종특별자치시', 1, 8);