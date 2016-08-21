<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://fb.me/react-0.13.0.js"></script>
    <script src="http://fb.me/JSXTransformer-0.13.0.js"></script>
    <script type="text/jsx">

        var Login = React.createClass({
            getInitialState: function () {
                return {tokenInfo: JSON.parse(localStorage.getItem('tokenInfo') || '{}')};
            },

            showInfo: function (info) {
                var self = this;
                this.setState({info: info});
                setTimeout(function() {
                    self.setState({info: ''})
                }, 4000);
            },

            showError: function (error) {
                this.setState({error: error})
            },

            clearError: function () {
                this.setState({error: ""})
            },

            saveTokenInfo: function (tokenInfo) {
                localStorage.setItem('tokenInfo', JSON.stringify(tokenInfo));
                this.setState({tokenInfo: tokenInfo});
            },

            logout: function() {
                this.saveTokenInfo("");
                this.setState({
                    query: '',
                    tweets: ''
                });
            },

            login: function (e) {
                var self;

                e.preventDefault();
                self = this;

                var data = {
                    username: this.state.login,
                    password: this.state.pass
                };
                $.ajax({
                    type: 'POST',
                    url: '/api/login',
                    data: data
                })
                        .done(function (data) {
                            self.saveTokenInfo(data);
                            self.clearForm();
                            self.clearError();
                        })
                        .fail(function (jqXhr) {
                            self.showError("Failed to login.")
                        });

            },

            register: function (e) {
                var self;

                e.preventDefault();
                self = this;

                var data = {
                    username: this.state.login,
                    password: this.state.pass
                };
                $.ajax({
                    type: 'POST',
                    url: '/register/consumer',
                    data: data
                })
                        .done(function (data) {
                            if(data.success) {
                                self.clearError();
                                self.showInfo('User successfully register, please login for next action.');
                            } else {
                                self.showError(data.message);
                            }
                        })
                        .fail(function (jqXhr) {
                            self.showError('Failed to register.');
                        });

            },

            search: function (e) {
                e.preventDefault();
                this.setState({tweets: ''});
                this.searchAction();
            },

            loadMore: function (e) {
                e.preventDefault();
                this.searchAction(this.state.nextMaxId);
            },


            searchAction: function (nextMaxId) {
                var self = this;

                var data = {
                    q: this.state.query,
                    maxId: nextMaxId
                };

                $.ajax({
                    type: 'POST',
                    url: '/api/twitter/search',
                    headers: {
                        'Authorization':this.state.tokenInfo.token_type + ' ' + this.state.tokenInfo.access_token
                    },
                    data: data
                })
                        .done(function (data) {
                            if(data.tweets) {
                                self.setState({
                                    tweets: (self.state.tweets) ? self.state.tweets.concat(data.tweets) : data.tweets,
                                    nextMaxId: data.nextMaxId
                                });
                                self.clearError();
                                if(!nextMaxId && !data.tweets.length) self.showInfo('No result, try another query.');
                            }
                        })
                        .fail(function (jqXhr) {
                            if (jqXhr.status === 401) {
                                self.logout();
                            } else {
                                self.showError('Failed to load tweets.');
                            }
                        });

            },

            clearForm: function () {
                this.setState({
                    login: "",
                    pass: ""
                });
            },

            loginChange: function (e) {
                this.setState({login: e.target.value})
            },

            passwordChange: function (e) {
                this.setState({pass: e.target.value})
            },

            queryChange: function (e) {
                this.setState({query: e.target.value})
            },

            render: function () {
                return (
                        <div className="container" style={{paddingTop: 40, width: 500}}>
                            {this.state.info &&
                            <div className="alert alert-success">
                                {this.state.info}
                            </div>}
                            {this.state.error &&
                            <div className="alert alert-danger">
                                {this.state.error}
                            </div>}
                            {!this.state.tokenInfo &&
                            <form onSubmit={this.submit}>
                                <div className="form-group row">
                                    <label for="login" className="col-sm-2 col-form-label">Login:</label>
                                    <div className="col-sm-10">
                                        <input type="text" className="form-control" id="login"
                                               onChange={this.loginChange} value={this.state.login}/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label for="pwd" className="col-sm-2 col-form-label">Password:</label>
                                    <div className="col-sm-10">
                                        <input type="password" className="form-control" id="pwd"
                                               onChange={this.passwordChange} value={this.state.pass}/>
                                    </div>
                                </div>
                                <div className="row-fluid centered">
                                    <button onClick={this.login} className="btn btn-primary" style={{marginRight: 20}}>Login</button>
                                    <button onClick={this.register} className="btn btn-primary">Register</button>
                                </div>
                            </form>}
                            {this.state.tokenInfo &&
                            <div>
                                <div className="row text-center">
                                    <div className="row-fluid">
                                        <label style={{marginRight: 20}}><strong>{'You logged as: ' + this.state.tokenInfo.username}</strong></label>
                                        <a onClick={this.logout} className="btn btn-primary btn-xs">Logout</a>
                                    </div>
                                </div>
                                <div className="row" style={{marginBottom: 20}}>
                                    <div className="col-sm-9">
                                        <input type="text" className="form-control"
                                                    onChange={this.queryChange} value={this.state.query}/>
                                    </div>
                                    <div className="col-sm-2">
                                        <button onClick={this.search} className="btn btn-primary">Search</button>
                                    </div>
                                </div>
                                {this.state.tweets &&
                                    this.state.tweets.map(function(tweet){
                                        return <div className="panel panel-info">
                                            <div className="panel-heading">
                                                {tweet.user.miniProfileImageURL &&
                                                <img src={tweet.user.miniProfileImageURL} style={{marginRight: 10}}/>
                                                }
                                                <label>{tweet.user.screenName}</label>
                                            </div>
                                            <div className="panel-body">tweets
                                                <p>{tweet.text}</p>
                                            </div>
                                        </div>;
                                    })
                                }
                                {this.state.tweets && this.state.nextMaxId &&
                                    <div className="text-center" style={{marginBottom: 20}}>
                                        <button onClick={this.loadMore} className="btn btn-primary">Load more</button>
                                    </div>
                                }
                             </div>

                            }
                        </div>

                );
            }
        });

        React.render(<Login/>, document.body);
    </script>
</head>

<body>
</body>