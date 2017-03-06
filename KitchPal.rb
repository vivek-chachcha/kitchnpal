require 'sinatra'
require 'json'
require_relative 'Users.rb'

get '/users/:email' do
	return_msg = {}
	result = get_user(params['email'])
	if (result == nil)
		return_msg[:status] = 'invalid user id'
	else
		return_msg[:status] = 'success'
		return_msg[:user] = result.to_s
	end
	return_msg.to_json
end

post '/users' do
	return_msg = {}
	if (params.key?("name") && params.key?("email") && params.key?("password"))
		result = create_user(params)
                return_msg[:user] = result
	else
		return_msg[:status] = 'required parameters - name, email, password - not provided'
	end
	return_msg[:parameters] = params.to_s
	return_msg.to_json
end
