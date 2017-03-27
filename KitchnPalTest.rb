ENV['RACK_ENV'] = 'test'

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

end
