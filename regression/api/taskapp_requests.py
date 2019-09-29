import requests
import json


def post_task(payload, url, headers={'Content-type': 'application/json'}):
    return requests.post(url=url, data=json.dumps(payload), headers=headers)


def patch_task(payload, url, headers={'Content-type': 'application/json'}):
    return requests.patch(url=url, data=json.dumps(payload), headers=headers)


def put_task(payload, url, headers={'Content-type': 'application/json'}):
    return requests.put(url=url, data=json.dumps(payload), headers=headers)


def delete_task(url, headers={'Content-type': 'application/json'}):
    return requests.delete(url=url, headers=headers)


def get_task_from_url(url, headers={'Accept': 'application/json'}):
    return requests.get(url=url, headers=headers)
