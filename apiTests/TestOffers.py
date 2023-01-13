import requests

from TestLectures import add_lecture, get_lectures, \
    signup_for_lecture, opt_out_from_lecture, remove_lecture
from login_fixture import login, login_user2, login_user1


def create_offer(sessionKey,
                 seller_id,
                 offer_lecture_id,
                 returned_lecture_id, ):
    "create offer"
    url = "http://localhost:8080/api/offers?sessionKey=" + sessionKey
    payload = {
        "sellerId": seller_id,
        "offeredLectureId": offer_lecture_id,
        "returnedLectureId": returned_lecture_id

    }
    headers = {"Content-Type": "application/json"}

    response = requests.post(url, json=payload, headers=headers)
    assert response.status_code == 200
    return response.json()


def accept_offer(sessionKey, offer_id, buyer_id):
    "accept offer"
    url = "http://localhost:8080/api/offers/accept/" + str(offer_id) + "?sessionKey=" + sessionKey
    headers = {"Content-Type": "application/json"}
    payload = str(buyer_id)

    response = requests.put(url, headers=headers, json=payload)
    assert response.status_code == 200

def get_offers(sessionKey):
    "get offers"
    url = "http://localhost:8080/api/offer" + "?sessionKey=" + sessionKey

    response = requests.get(url)
    assert response.status_code == 200
    return response.json()

class TestOffers:

    def test_get_offers(self, login):
        url = "http://localhost:8080/api/offers"
        response = requests.get(url)
        assert response.status_code == 200
        return response.json()


def test_offers_procedure():
    # share variables
    # login
    user1_id = 1
    sessionKey1 = login_user1()
    user2_id = 2
    sessionKey2 = login_user2()

    # prepare - add two lectures
    add_lecture(sessionKey1)
    add_lecture(sessionKey1)

    # get all lectures
    all_lectures = get_lectures(sessionKey1)
    lecture_1_id = all_lectures[-2]["id"]
    lecture_2_id = all_lectures[-1]["id"]

    # sign up for lectures
    signup_for_lecture(sessionKey1, lecture_1_id)
    signup_for_lecture(sessionKey2, lecture_2_id)

    # create offer
    create_offer(sessionKey1, user1_id, lecture_1_id, lecture_2_id)

    # accept offer
    offer_id = get_offers(sessionKey1)[-1]["id"]
    accept_offer(sessionKey2, offer_id, user2_id)

    # clean up
    opt_out_from_lecture(sessionKey1, lecture_2_id)
    opt_out_from_lecture(sessionKey2, lecture_1_id)
    remove_lecture(sessionKey1, lecture_1_id)
    remove_lecture(sessionKey1, lecture_2_id)