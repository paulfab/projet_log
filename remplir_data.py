import sqlite3

con = sqlite3.connect('directions.db')

cursor = con.cursor()

cursor2 = con.cursor()

cursor.execute("SELECT * FROM direction")
for line in cursor.fetchall():
	lindict = eval(line[0])[0]
	# print(lindict["legs"][0]["start_address"])
	resultat = ""
	steps = lindict["legs"][0]["steps"]
	for step in steps:
		resultat += step["html_instructions"] + "<br>"
	print(type(line[2]))	
	cursor2.execute("""INSERT INTO chemin(from_,to_,instructions,distance) VALUES(?,?,?,?)""", (line[1], line[2], resultat, lindict["legs"][0]["distance"]["value"]))
	print(line[1], line[2])
con.commit()
