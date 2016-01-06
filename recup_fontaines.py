import googlemaps
import csv
import sqlite3


# gmaps = googlemaps.Client(key="AIzaSyD5J9c5IOVliJb-XcOtvJ5KDfqncCo2Yyw")

# gmaps = googlemaps.Client(key='AIzaSyBKA5e5PUt5ZHW-9M0EpUpkxccmaezu3zU')

gmaps = googlemaps.Client(key='AIzaSyAaDKPZC-g91KcOzC1BRz-Krr5htklrGUw')
con = sqlite3.connect('directions.db')
cursor = con.cursor()

csvfile = open('fontaines_sans_header.csv', 'r')
csvreader = csv.reader(csvfile, delimiter=',')
ind1 = 1
csvreader2 = csvreader
print(csvreader)
csvreader = list(csvreader)

for row in csvreader:
		ind2 = 1
		for row2 in csvreader:		
			cursor.execute("SELECT to_,from_ FROM direction WHERE from_=? AND to_ = ?", (ind1, ind2))
			con.commit()
			colone = cursor.fetchone()
			print(colone)
			if not colone:
				from__ = float(row[1]), float(row[2])
				to__ = float(row2[1]), float(row2[2])
				geocode_result = gmaps.directions(from__, to__, mode='walking')
				cursor.execute("INSERT INTO direction VALUES(?,?,?)", (str(geocode_result), ind1, ind2))
				con.commit()
				print(ind1, from__, ind2, to__)
			ind2 = ind2 + 1
		ind1 = ind1 + 1
