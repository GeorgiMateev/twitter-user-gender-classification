import twitter


# Authentication parameters - fill manually! You need to create a Twitter app in dev.twitter.com
# in order to be given these OAUTH2 parameters for the authentication (mandatory) to your app
# Reference: https://aaronparecki.com/oauth-2-simplified/#creating-an-app (general example, but applies for twitter)
api = twitter.Api(
    consumer_key='<consumer_key>',
    consumer_secret='<consumer_secret>',
    access_token_key='<access_token_key>',
    access_token_secret='<access_token_secret>')

names_found_file = open('firstnames.txt', 'w')
names_found_file.write('#<first_name_if_found>=<username>')
sample = 0
for line in open('test/usernames.txt', 'r'):
    try:
        names_found_file.write(api.GetUser(screen_name='@' + line).__getattribute__('name') + '=' + line)
        sample = sample + 1
        names_found_file.flush()

        # Verbose output for debugging
        print(api.GetUser(screen_name='@' + line).__getattribute__('name') + '=' + line)
        print(sample)
    except:
        names_found_file.write('=' + line)
        sample = sample + 1
        names_found_file.flush()

        # Verbose outputfor debugging
        print('=' + line)
        print(sample)

names_found_file.close()