<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EET</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.1.10/vue.js"></script>
    <script src="https://cdn.jsdelivr.net/vue.resource/1.0.3/vue-resource.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
<nav  class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div id="navibar" class="navbar-header">
            <a class="navbar-brand" href="#">EET application</a>
            <a id="logButton" class="navbar-brand" href="#" onclick="app.logIn()">Login</a>
            <a id="registButton" class="navbar-brand" href="#" onclick="app.register()">Sign Up</a>
        </div>
    </div>
</nav>

<div id="app" class="container">
    <div id="logg">
        <h2>Prihlasovani</h2>
        <br>
        <div class="col-md-4">
            <form class="form" id="search-form" v-on:submit.prevent="log">
                <div class="form-group form-group-lg">
                    <label>Login</label>
                    <input type="text" class="form-control" v-model="login.username">
                </div>
                <div class="form-group form-group-lg">
                    <label>Password</label>
                    <input type="password" class="form-control" v-model="login.password">
                </div>

                <div class="form-group">
                    <div class="col-md-offset-2 col-sm-10">
                        <button type="submit" id="bth-search" class="btn btn-primary btn-lg">Log-in</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="regg" style="position:absolute; visibility:hidden">
        <h2>Registrace</h2>
        <br>
        <div class="col-md-10">
            <form class="form" id="regist-form" v-on:submit.prevent="reg">
                <div class="form-group form-group-lg">
                    <label>Login</label>
                    <input type="text" class="form-control" v-model="registration.username">
                </div>
                <div class="form-group form-group-lg">
                    <label>Password</label>
                    <input type="password" class="form-control" v-model="registration.password">
                </div>
                <div class="form-group form-group-lg">
                    <label>Password again</label>
                    <input type="password" class="form-control" v-model="registration.password2">
                </div>
                <div class="form-group form-group-lg">
                    <label>Email</label>
                    <input type="text" class="form-control" v-model="registration.email">
                </div>
                <div class="form-group form-group-lg">
                    <label>Company name</label>
                    <input type="text" class="form-control" v-model="registration.companyName">
                </div>
                <div class="form-group form-group-lg">
                    <label>ICO</label>
                    <input type="text" class="form-control" v-model="registration.companyId">
                </div>
                <div class="form-group form-group-lg">
                    <label>DIC</label>
                    <input type="text" class="form-control" v-model="registration.vatId">
                </div>

                <div class="form-group">
                    <div class="col-md-offset-2 col-sm-10">
                        <button type="submit" id="bth-registrate" class="btn btn-primary btn-lg">Sign Up</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div v-show="userInfo">
        <div class="row">
            <div class="col-sm-3 col-md-2" v-for="user in userInfo">Firma: {{user.companyName}} <br/>ICO:
                {{user.companyId}} <br/>DIC: {{user.vatId}}
            </div>
            <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
                <h2>Zaznamy:</h2>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <th>Datum</th>
                        <th>Cena</th>
                        <th>Fik</th>
                        <th>Bkp</th>
                        <th>Tisk</th>
                        </thead>
                        <tbody>
                        <tr v-for="(receipt, index) in receipts" >
                            <td>{{receipt.date}}</td>
                            <td>{{receipt.value}}</td>
                            <td>{{receipt.fik}}</td>
                            <td>{{receipt.bkp}}</td>
                            <td v-on:click="printer({index})" style="cursor:pointer;color:red">Print</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
</div>

<script>
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello world',
            login: {
                username: '',
                password: ''

            },
            registration: {
                username: '',
                password: '',
                password2: '',
                email: '',
                companyName: '',
                companyId: '',
                vatId: ''
            },
            confirmationRegistration: {
                error: 0,
                email: ''
            }
            ,
            logged: false,
            userInfo: null,
            receipts: null
        },
        methods: {
            log: function () {
                this.loginResource.get(this.login).then((response) => {
                    this.logged = response.body;
                console.log(this.logged);
                if (this.logged) {
                    var d = document.getElementById("logg");
                    var lb = document.getElementById("logButton");
                    var rB = document.getElementById("registButton");
                    document.getElementById("app").removeChild(d);
                    document.getElementById("navibar").removeChild(lb);
                    document.getElementById("navibar").removeChild(rB);
                    this.getInfoResource.get(this.login).then((response) => {
                        this.userInfo = (response.body)

                })

                    this.receiptInfoResource.get(this.login).then((response) => {
                        this.receipts = (response.body)
                })
                }
            })
                ;
            },
            reg: function () {
                if (this.registration.password !== this.registration.password2) {
                    alert("Neshoduji se hesla.")
                    return;
                }

                this.createResource.get(this.registration).then ((response) => {
                    this.confirmationRegistration = (response.body)
                })

                if (this.confirmationRegistration.error !== 0)
                    alert("Chyba.")
                else {
                    alert("Byl jste uspesne registrovan.");
                    window.location.reload();
                }


            },
            register: function () {
                var d = document.getElementById("regg");
                var l = document.getElementById("logg");
                d.style.left = l.offsetLeft + "px";
                d.style.top = l.offsetTop + "px";
                l.style.visibility = "hidden";
                d.style.visibility = "visible";
            },
            logIn: function () {
                console.log("registering");
                var d = document.getElementById("regg");
                var l = document.getElementById("logg");
                l.style.visibility = "visible";
                d.style.visibility = "hidden";
            },
            printer: function(index) {
                console.log(index.index);
                var i = index.index;
                console.log( "mojeuctenka/"+this.userInfo[0].companyId+"/"+this.receipts[i].date);
                console.log(this.userInfo[0].companyId)
                console.log(this.receipts)
                window.location.href = "mojeuctenka/"+this.userInfo[0].companyId+"/"+this.receipts[index.index].date;
            }
        },
        created: function () {
            this.loginResource = this.$resource('/server/authenticateUnencrypted');
            this.getInfoResource = this.$resource('/server/getInfo');
            this.receiptInfoResource = this.$resource('/server/getReceiptsForUser');
            this.createResource = this.$resource('server/registration');
        }
    });

</script>
</body>
</html>