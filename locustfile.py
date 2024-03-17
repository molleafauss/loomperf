from locust import FastHttpUser, task
import random

class TestService(FastHttpUser):

    @task
    def test(self):
        data = {
            "name": f"test{random.randint(0, 1000)}",
            "tag": "".join([chr(ord('a') + random.randint(0, 26)) for i in range(4)])
        }
        self.client.post("/user/create", json=data)
