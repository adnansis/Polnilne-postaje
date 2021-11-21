INSERT INTO uporabnik (tip, uporabnisko_ime) VALUES ('lastnik', 'elonmusk');
INSERT INTO uporabnik (tip, uporabnisko_ime) VALUES ('uporabnik', 'billgates');
INSERT INTO uporabnik (tip, uporabnisko_ime) VALUES ('uporabnik', 'jeffbezos');

INSERT INTO postaja (id_uporabnik, lokacija, ime, moc, obratuje_od, obratuje_do, cena, na_voljo) VALUES (1, 'Večna pot 113', 'TYPE 2', 22.0, '00:00:00', '23:59:59', 0.15, TRUE);
INSERT INTO postaja (id_uporabnik, lokacija, ime, moc, obratuje_od, obratuje_do, cena, na_voljo) VALUES (1, 'Večna pot 113', 'TYPE 2', 22.0, '00:00:00', '23:59:59', 0.15, TRUE);
INSERT INTO postaja (id_uporabnik, lokacija, ime, moc, obratuje_od, obratuje_do, cena, na_voljo) VALUES (1, 'Večna pot 113', 'TYPE 2 Tesla', 150.0, '00:00:00', '23:59:59', 0.9, TRUE);

INSERT INTO termin (id_uporabnik, id_postaja, termin_od, termin_do) VALUES (1, 2, '2021-12-01 10:00:00', '2021-12-01 11:30:00');
INSERT INTO termin (id_uporabnik, id_postaja, termin_od, termin_do) VALUES (1, 2, '2021-12-05 10:00:00', '2021-12-01 11:30:00');