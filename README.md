# giftandgain-backend

H2 Temp Database:
http://localhost:8001/h2-console/login.do?jsessionid=7444250db2275732d45074b2ccb679be

API listing: 
http://localhost:8001/swagger-ui/index.html //for swagger ui

Listing: 
http://localhost:8001/giftandgain/items
http://localhost:8001/giftandgain/category

Download Report: 
http://localhost:8001/giftandgain/download/report/09/2023

Sorting by expiryDate in descending order: 
http://localhost:8001/giftandgain/items?page=0&size=10&sortBy=expiryDate&direction=desc

Sorting by itemName in ascending order:
http://localhost:8001/giftandgain/items?page=0&size=10&sortBy=itemName&direction=asc

Get low priority list for the selected month and year: 
http://localhost:8001/giftandgain/category/lowpriority/09/2023

Get high priority list for the selected month and year: 
http://localhost:8001/giftandgain/category/highpriority/09/2023
