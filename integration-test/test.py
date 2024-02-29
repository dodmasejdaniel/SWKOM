import time

import pytest
import requests

BASE_URL = "http://localhost:8081"


@pytest.fixture
def test_files():
    return ["testPositive.pdf", "testNegative.pdf"]


def test_upload_document(test_files):
    url = f"{BASE_URL}/api/documents/post_document/"
    for file_name in test_files:
        with open(file_name, "rb") as file:
            files = {"document": file}
            response = requests.post(url, files=files)
            assert response.status_code == 201


def get_list_of_retrieved_filenames(response):
    return list(map(lambda entry: entry["title"], response.json()["results"]))


def test_get_documents():
    url = f"{BASE_URL}/api/documents/"
    response = requests.get(url)

    assert response.status_code == 200

    list_of_retrieved_filenames = get_list_of_retrieved_filenames(response)
    assert "testPositive.pdf" in list_of_retrieved_filenames
    assert "testNegative.pdf" in list_of_retrieved_filenames


def test_search_documents():
    url = f"{BASE_URL}/api/documents/"
    params = {"query": "Integrationtests"}
    response = requests.get(url, params=params)

    list_of_retrieved_filenames = get_list_of_retrieved_filenames(response)

    assert response.status_code == 200
    assert "testPositive.pdf" in list_of_retrieved_filenames
    assert "testNegative.pdf" not in list_of_retrieved_filenames


def test_get_document_by_id():
    url = f"{BASE_URL}/api/documents/"
    response = requests.get(url)
    assert response.status_code == 200

    document_id = None
    for document in response.json()["results"]:
        if document["title"] == "testPositive.pdf":
            document_id = document["id"]
            break
    assert document_id is not None

    url = f"{BASE_URL}/api/documents/{document_id}/"
    response = requests.get(url)
    # Delay a bit to give ocr enough time to process and write content
    time.sleep(3)
    assert response.status_code == 200
    assert response.json()["title"] == "testPositive.pdf"
    assert response.json()["content"].strip() == "Integrationtests in Python!"
