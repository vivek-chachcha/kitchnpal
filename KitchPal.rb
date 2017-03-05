require 'sinatra'
require 'Users'

get '/users/:uid' do
	return_msg = {}
	result = get_user(params['uid'])
	if (result == null)
		return_msg[:status] = 'invalid user id'
	else
		return_msg[:status] = 'success'
	return_msg[:user] = result.to_json
	return_msg.to_json
end
