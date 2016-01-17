import sqlite3

con = sqlite3.connect('directions.db')

cursor = con.cursor()
f = open('vrai_fontaine.csv','w')
cursor.execute("SELECT * FROM chemin")
for line in cursor.fetchall():
	f.write(str(line[0]) + "," + str(line[1]) + "," + str(line[3]) +"," + line[2]+ "\n")
	# print(lindict["legs"][0]["start_address"])
	resultat = ""
	print(line)
	#steps = lindict["legs"][0]["steps"]
	#for step in steps:
	#	resultat += step["html_instructions"] + "<br>"
	#print(type(line[2]))	
	#cursor2.execute("""INSERT INTO chemin(from_,to_,instructions,distance) VALUES(?,?,?,?)""", (line[1], line[2], resultat, lindict["legs"][0]["distance"]["value"]))
	#print(line[1], line[2])
#con.commit()
