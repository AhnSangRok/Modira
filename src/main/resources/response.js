const axios = require('axios').default;

// 지역별 게시글 조회
function getLocalPost(locationName) {
    let name = locationName;
    axios.get(`/api/post/local/${name}`)
        .then(function (response) {
            for (let i = 0; i < response.length; i++) {
                let row = response[i];
                let title = row.title;
                let partyNum = row.partyNum;
                let imageUrl = row.imageUrl;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

function getPost() {
    axios.get(`/api/post`)
        .then(function (response) {
            for (let i = 0; i < response.length; i++) {
                let row = response[i];
                let title = row.title;
                let partyNum = row.partyNum;
                let imageUrl = row.imageUrl;
                console.log(title, partyNum, imageUrl);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

//게시글 작성
function post() {
    let imageUrl = $('#imageUrl').val();
    let title = $('#title').val();
    let locationName = $('#location').val();
    let partyNum = $('#partyNum').val();
    let contents = $('#contents').val();

    let data = {
        "imageUrl": imageUrl,
        "title": title,
        "locationName": locationName,
        "partyNum": partyNum,
        "contents": contents
    }

    axios.post(`/api/post`, data)
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}

// 로그인 하기
function login() {
    let userId = $('#userId').val();
    let password = $('#password').val();

    let data = {"username": userId, "password": password};

    axios.post(`/user/login`, data)
        .then(function (response) {
            const jwtToken = response.getResponseHeader("Authorization");
            if (jwtToken) {
                $.cookie("token", jwtToken);
                $.ajaxSetup({
                    headers:{
                        'Authorization': $.cookie('token', jwtToken, { path: '/' })
                    }
                });
                window.location.href = '/';
            } else {
                window.location.href = '/user/loginView?error';
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

//회원가입 하기
function signup() {
    let userId = $('#userId').val();
    let password = $('#password').val();
    let password2 = $('#password2').val();

    let data = {"username": userId, "password": password, "password2": password2};

    axios.create(`/user/signup`, data)
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
}

//게시판 디테일 페이지
function detailPost(postId) {
    axios.get(`/api/post/${postId}`)
        .then(function (response) {
            let title = response.title;
            let imageUrl = response.imageUrl;
            let partyNum = response.partyNum;
            let joinNum = response.joinNum;
            let contents = response.contents;
            let locationName = response.locationName;
            let createdAt = response.createdAt;
            let modifiedAt = response.modifiedAt;
        })
        .catch(function (error) {
            // handle error
            console.log(error);
        });
}

// 참여(좋아요) 누를시
function join(postId) {
    let username = $('#username').val();
    let data = {"username" : username}

    axios.post(`api/likes/${postId}`, data)
        .then(function (response) {
            console.log(response);
        })
}

//로그인시 토큰 받기 , username 받기
$(document).ready(function () {
    if ($.cookie('token')) {
        $.ajaxSetup({
            headers: {
                'Authorization': $.cookie('token')
            }
        })
    } else {
        window.location.href = '/user/login';
    }

    $.ajax({
        type: "POST",
        url: `/user/userinfo`,
        contentType: "application/json",
        success: function (response) {
            const username = response.username;
            if (!username) {
                window.location.href = '/user/loginView';
            }

            $('#username').text(username);

        },
        error: function () {
            window.location.href = '/user/loginView';
        }
    })
})