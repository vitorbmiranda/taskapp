from re import match
from distutils.util import strtobool
from behave import *
from api.taskapp_requests import *


@given('properties are loaded and environment is healthy')
def background(context):
    context.base_url = context.config.userdata['url']


@when('a task is created with description as {description} and resolved as {resolved}')
def create_task(context, description, resolved):
    res = post_task({"description": description, "resolved": resolved}, context.base_url)
    context.res = res
    context.task_location = res.headers['Location']


@when('the created task description is put with description as {description} and resolved as {resolved}')
def put_entire_task(context, description, resolved):
    res = put_task({"description": description, "resolved": resolved}, context.task_location)
    context.res = res
    context.task_location = res.headers['Location']


@when('the created task is retrieved')
def retrieve_task(context):
    context.res = get_task_from_url(context.task_location)


@when('the created task description is patched to {description}')
def update_task(context, description):
    context.res = patch_task({"description": description}, context.task_location)


@when('the created task is deleted')
def remove_task(context):
    context.res = delete_task(context.task_location)


@then('the response code should be {code:d}')
def check_response_code(context, code):
    res = context.res
    assert res.status_code == code


@then('the location header should be properly set')
def check_location_header(context):
    res = context.res
    location_header = res.headers['Location']
    pattern = "{}/(\\d)".format(context.base_url)
    m = match(pattern, location_header)
    assert m is not None


@then('the response \'description\' field should be {description}')
def check_description(context, description):
    res_description = context.res.json()['description']
    assert res_description == description


@then('the response \'resolved\' field should be {resolved}')
def check_resolved(context, resolved):
    res_resolved = context.res.json()['resolved']
    assert strtobool(resolved) == res_resolved


@then('the response \'createdAt\' and \'updatedAt\' fields should be set and be the same')
def check_created_updated_same(context):
    created_at = context.res.json()['createdAt']
    updated_at = context.res.json()['updatedAt']
    assert created_at is not None
    assert updated_at is not None
    assert created_at == updated_at


@then('the response \'updatedAt\' should be different than \'createdAt\'')
def check_created_updated_different(context):
    created_at = context.res.json()['createdAt']
    updated_at = context.res.json()['updatedAt']
    assert created_at is not None
    assert updated_at is not None
    assert created_at != updated_at
