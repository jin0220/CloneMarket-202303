# -*- coding: utf-8 -*-

import os
import pandas as pd
import pymysql

print(pd.__version__)

filename = 'location.xlsx'
filepath = os.path.join('C:\\', filename)

data = pd.read_excel(filepath, sheet_name = '서울특별시', header=1, engine = 'openpyxl') #"C:\Users\hhh73\OneDrive\바탕 화면\행정구역별_위경도_좌표.xlsx"

data = data.fillna(0)

conn = pymysql.connect(host='localhost', user='', passwd='', db='')
curs = conn.cursor(pymysql.cursors.DictCursor)

sql = 'insert into location (district, city, town, township, village, latitude, longitude) values(%s, %s, %s, %s, %s, %s, %s)'
for idx in range(len(data)):
	curs.execute(sql, tuple(data.values[idx]))
conn.commit()

#종료
curs.close()
conn.close()