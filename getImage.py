import base64
import requests

def decodeImage(img):
    
    image_64_decode = base64.decodebytes(bytearray(img,'utf-8'))

    image_result = open('logo1.jpg', 'wb')
    image_result.write(image_64_decode)

url = "http://localhost:8080/api/ap"
r = requests.get(url)
print(r.status_code)
json_data = r.json()
decodeImage(json_data[0]["image"])