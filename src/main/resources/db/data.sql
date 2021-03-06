INSERT INTO category (id, title, description)
VALUES ('042a8ce7-164c-4899-913d-09801524197d', 'Horror', 'Horror fiction');

INSERT INTO author (id, name, about)
VALUES ('5494e9d2-315f-4675-a232-80fbbb467248', 'Stephen King',
        'Stephen Edwin King (born September 21, 1947) is an American author of horror, supernatural fiction, suspense, crime, science-fiction, and fantasy novels. Described as the "King of Horror", a play on his surname and a reference to his high standing in pop culture, his books have sold more than 350 million copies, and many have been adapted into films, television series, miniseries, and comic books. King has published 64 novels, including seven under the pen name Richard Bachman, and five non-fiction books. He has also written approximately 200 short stories, most of which have been published in book collections. King has received Bram Stoker Awards, World Fantasy Awards, and British Fantasy Society Awards. In 2003, the National Book Foundation awarded him the Medal for Distinguished Contribution to American Letters. He has also received awards for his contribution to literature for his entire bibliography, such as the 2004 World Fantasy Award for Life Achievement and the 2007 Grand Master Award from the Mystery Writers of America. In 2015, he was awarded with a National Medal of Arts from the U.S. National Endowment for the Arts for his contributions to literature.');

INSERT INTO book (id, title, description, isbn, author_id, category_id)
VALUES ('bef7379d-091f-458d-a2c0-521049c69298', 'The Institute', 'NO ONE HAS EVER ESCAPED FROM THE INSTITUTE.', '9781529355390', '5494e9d2-315f-4675-a232-80fbbb467248', '042a8ce7-164c-4899-913d-09801524197d');

INSERT INTO customer (id, name, address1, address2, address3, address4, post_code, telephone, email, version, created, updated)
VALUES ('c5ce09ae-1389-4fe8-9d1b-100415cf0bac', 'Andrew Smith', 'Address 1', 'Address 2', 'Address 3', 'Address 4', 'PC12 1AA', '020 100 1000', 'andrew.smith@email.com', 1, '2022-07-22 12:00', '2022-07-22 14:00');

INSERT INTO customer (id, name, address1, address2, address3, address4, post_code, telephone, email, version, created, updated)
VALUES ('7408fe93-7557-4281-99ff-f3c2fd38354d', 'Laura Smith', 'Address 1', 'Address 2', 'Address 3', 'Address 4', 'PC12 1AB', '020 100 2000', 'laura.smith@email.com', 1, '2022-07-22 12:00', '2022-07-22 14:00');

INSERT INTO customer (id, name, address1, address2, address3, address4, post_code, telephone, email, version, created, updated)
VALUES ('9f613b69-b09c-48f3-ba57-37ec1c417ef7', 'Lee Jones', 'Address 1', 'Address 2', 'Address 3', 'Address 4', 'PC12 1AC', '020 100 3000', 'lee.jones@email.com', 1, '2022-07-22 12:00', '2022-07-22 14:00');

