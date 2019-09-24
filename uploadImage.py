import base64
import requests

url = "http://localhost:8080/api/ap"
headers = {'Content-type': 'application/json'}

with open('test_image_1.jpg', mode='rb') as file:
    img = file.read()
img = base64.encodebytes(img).decode("utf-8")


json_data = {
                "imageID" : "image_test_1",
                "userID" : "user2",
                "latitude" : 123.324,
                "longitude" : 2525.55,
                "timeStamp" : "2014-01-01T00:00:00",
                "image" : img
            }    

r = requests.post(url, json=json_data,headers=headers)
print(r.text)