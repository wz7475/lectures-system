import uuid

import requests
from login_fixture import login

def remove_lecture(login, lec_id):
    "remove lecture"
    url = "http://localhost:8080/api/lectures?sessionKey=" + login + "&lectureId=" + str(lec_id)
    headers = {"Content-Type": "application/json"}

    response = requests.delete(url, headers=headers)
    assert response.status_code == 200
    return response.status_code


def add_lecture(login):
    "add lecture"
    url = "http://localhost:8080/api/lectures?sessionKey=" + login
    payload = {
        "name": str(uuid.uuid4()),
        "dayOfWeek": 1,
        "beginTimeHour": 12,
        "beginTimeMinute": 15,
        "durationHours": 1,
        "durationMinutes": 0
    }
    headers = {"Content-Type": "application/json"}

    response = requests.post(url, json=payload, headers=headers)
    assert response.status_code == 200


def get_lectures(login):
    "get lectures"
    url = "http://localhost:8080/api/lectures"
    headers = {"Content-Type": "application/json"}

    response = requests.get(url, headers=headers)
    assert response.status_code == 200
    return response.json()

def signup_for_lecture(login, lec_id):
    "signup for lecture"
    url = "http://localhost:8080/api/lectures/signup?sessionKey=" + login + "&lectureId=" + str(lec_id)
    headers = {"Content-Type": "application/json"}

    response = requests.post(url, headers=headers)
    assert response.status_code == 200
    return response.status_code

def opt_out_from_lecture(login, lec_id):
    "opt out from lecture"
    url = "http://localhost:8080/api/lectures/optout?sessionKey=" + login + "&lectureId=" + str(lec_id)
    headers = {"Content-Type": "application/json"}

    response = requests.delete(url, headers=headers)
    assert response.status_code == 200
    return response.status_code

def get_lecture_by_id(login, lec_id):
    "get lecture by id"
    url = "http://localhost:8080/api/lectures/" + str(lec_id)
    headers = {"Content-Type": "application/json"}

    response = requests.get(url, headers=headers)
    assert response.status_code == 200
    return response.json()

class TestLectures:

    def test_get_lectures(self, login):
        get_lectures(login)

    def test_get_lecture_by_id(self, login):
        get_lecture_by_id(login, 44)

    def test_signup_for_lecture(self, login):
        all_lectures = get_lectures(login)
        lecture_added_id = all_lectures[-1]["id"]
        signup_for_lecture(login, lecture_added_id)


    def test_opt_out_from_lecture(self, login):
        all_lectures = get_lectures(login)
        lecture_added_id = all_lectures[-1]["id"]
        opt_out_from_lecture(login, lecture_added_id)

    def test_add_lecture(self, login):
        add_lecture(login)

    def test_remove_lecture(self, login):
        all_lectures = get_lectures(login)
        lecture_added_id = all_lectures[-1]["id"]
        remove_lecture(login, lecture_added_id)
