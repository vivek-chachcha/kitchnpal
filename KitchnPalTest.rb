ENV['RACK_ENV'] = 'test'

require 'json'
require_relative 'KitchnPal'
require 'test/unit'
require 'rack/test'

class KitchnPalTest < Test::Unit::TestCase
        include Rack::Test::Methods
	$email = ('a'..'z').to_a.shuffle[0,8].join

        def app
                Sinatra::Application
        end

        def test_create_user
		post '/users', params = {:name => "TestUser", :email => $email, :password => "testing"}
		assert last_response.ok?
		assert last_response.body.include?("TestUser")
		assert last_response.body.include?($email)
		$user = JSON.parse(last_response.body)['user']
	end

	def test_create_user_no_params
		post '/users'
		assert last_response.body.include?("required parameters")
	end

	def test_create_user_partial_params
		post '/users', params = {:name => "TestUser", :email => "random@gmail.com"}
		assert last_response.body.include?("required parameters")
	end

	def test_create_user_repeat_email
		post '/users', params = {:name => "TestUser", :email => $email, :password => "testing"}
		assert last_response.body.include?("User already exists")
	end

	def test_get_user_no_email
		get "/users/", params = {:accessToken => $user['accessToken']}
		assert !last_response.ok?
	end

	def test_get_user_no_accesstoken
		get "/users/" + $email
		assert last_response.body.include?("required parameters")
	end

	def test_get_user_invalid_token
		get "/users/" + $email, params = {:accessToken => $user['accessToken']+"123"}
		assert last_response.body.include?("invalid access token")
	end

	def test_get_user_invalid_email
		get "/users/" + $email + $email, params = {:accessToken => $user["accessToken"]}
		assert last_response.body.include?("invalid user")
	end

	def test_get_user
		get "/users/" + $email, params = {:accessToken => $user["accessToken"]}
		assert last_response.body.include?("success")
		assert JSON.parse(last_response.body)['user'] == $user.to_s
	end

	def test_update_user_no_access_token
		put "/users/" + $email, params = {}
		assert last_response.body.include?("Access token is not provided")
	end

	def test_update_user_invalid_access_token
		put "/users/" + $email, params = {:accessToken => $user['accessToken']+"123"}
		assert last_response.body.include?("invalid access token")
	end

	def test_update_user_no_email
		put "/users/"
		assert !last_response.ok?
	end

	def test_update_user_invalid_email
		put "/users/" + $email + $email, params = {:accessToken => $user['accessToken']}
		assert last_response.body.include?("User does not exist")
	end
	
	def test_update_user
		user2 = $user.dup
		user2['name'] = "TestUser2"
		user2['calPerDay'] = 500
		put "/users/" + $email, params = user2
		puts last_response.body
		puts $user
		assert JSON.parse(last_response.body)['user'] != $user
		assert JSON.parse(last_response.body)['user'] == user2
	end

end
