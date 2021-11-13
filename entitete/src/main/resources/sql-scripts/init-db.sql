--INSERT INTO uporabnik (id_uporabnik, tip, uporabnisko_ime) VALUES (1, 'lastnik', 'elonmusk');
--INSERT INTO uporabnik (id_uporabnik, tip, uporabnisko_ime) VALUES (2, 'uporabnik', 'billgates');
--INSERT INTO uporabnik (id_uporabnik, tip, uporabnisko_ime) VALUES (3, 'uporabnik', 'jeffbezos');

--INSERT INTO postaja (id_postaja, id_uporabnik, lokacija, ime, moc, obratuje_od, obratuje_do, cena, na_voljo) VALUES (1, 1, 'Večna pot 113', 'TYPE 2', 22.0, '00:00:00', '23:59:59', 0.15, TRUE);
--INSERT INTO postaja (id_postaja, id_uporabnik, lokacija, ime, moc, obratuje_od, obratuje_do, cena, na_voljo) VALUES (2, 1, 'Večna pot 113', 'TYPE 2', 22.0, '00:00:00', '23:59:59', 0.15, TRUE);
--INSERT INTO postaja (id_postaja, id_uporabnik, lokacija, ime, moc, obratuje_od, obratuje_do, cena, na_voljo) VALUES (3, 1, 'Večna pot 113', 'TYPE 2 Tesla', 150.0, '00:00:00', '23:59:59', 0.9, TRUE);

--INSERT INTO termin (id_termin, id_uporabnik, id_postaja, termin_od, termin_do) VALUES (1, 1, 2, '2021-12-01 10:00:00', '2021-12-01 11:30:00');
--INSERT INTO termin (id_termin, id_uporabnik, id_postaja, termin_od, termin_do) VALUES (2, 1, 2, '2021-12-05 10:00:00', '2021-12-01 11:30:00');