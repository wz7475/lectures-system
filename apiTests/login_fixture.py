import pytest
import requests


@pytest.yield_fixture(scope="class")
def login():
    "method to login, used once for all tests"
    url = "http://localhost:8080/api/user/login"
    payload = {"name": "admin", "password": "12345"}
    headers = {"Content-Type": "application/json"}

    response = requests.post(url, json=payload, headers=headers)

    # assert response.status_code == 200
    yield response.json()["sessionKey"]

    url = "http://localhost:8080/api/user/logout"
    payload = {"sessionKey": response.json()["sessionKey"]}
    requests.delete(url, json=payload)
    # assert response.status_code == 200


def login_user1():
    "method to login, used once for all tests"
    url = "http://localhost:8080/api/user/login"
    payload = {"name": "admin", "password": "12345"}
    headers = {"Content-Type": "application/json"}

    response = requests.post(url, json=payload, headers=headers)

    # assert response.status_code == 200
    return response.json()["sessionKey"]
def login_user2():
    "method to login, used once for all tests"
    url = "http://localhost:8080/api/user/login"
    payload = {"name": "user", "password": "12345"}
    headers = {"Content-Type": "application/json"}

    response = requests.post(url, json=payload, headers=headers)

    # assert response.status_code == 200
    return response.json()["sessionKey"]
