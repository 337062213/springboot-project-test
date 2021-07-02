            var ureg=/^[a-zA-Z0-9]{3,}[a-zA-Z0-9]$/;
            var treg=/^[130,131,132,137,138,150,151,152,158,173,187,190][0-9]{7}[0-9]$/;
            var ereg=/[@]{0}[A-Za-z]{3,}/g;

            // onUserNameBlur=function(text){
            onUserNameBlur=(text) =>{
                if(text.value==""){
                    text.nextElementSibling.innerHTML="用户名不能为空";
                    text.nextElementSibling.style.color="red";
                }
                else if((text.value!="")&&(text.value.length<4)){
                    text.nextElementSibling.innerHTML="用户名长度错误";
                    text.nextElementSibling.style.color="red";
                }
                else if(!ureg.test(text.value)){
                    text.nextElementSibling.innerHTML="用户名支持数字和字母，位数至少为四位"
                    text.nextElementSibling.style.color="red";
                }else{
                    text.nextElementSibling.innerHTML="用户名支持数字和字母，位数至少为四位";
                    text.nextElementSibling.style.color="blue";
                }
            }

            pwdonblur=function(text, passwordss){
                debugger;
                if(text.value==""){
                    text.nextElementSibling.innerHTML="密码不能为空";
                    text.nextElementSibling.style.color="red";
                }else if((text.value!="")&&(text.value.length<4)){
                    text.nextElementSibling.innerHTML="密码长度错误";
                    text.nextElementSibling.style.color="red";
                }else if(!ureg.test(text.value)){
                    text.nextElementSibling.innerHTML="密码输入错误";
                    text.nextElementSibling.style.color="red";
                }else if(passwordss && passwordss.value && passwordss.value!=text.value){
                    text.nextElementSibling.innerHTML="两次密码不一致";
                    text.nextElementSibling.style.color="red";
                }else{
                    text.nextElementSibling.innerHTML="密码支持数字和字母，位数至少为四位";
                    text.nextElementSibling.style.color="blue";
                    if(passwordss && passwordss.value && passwordss.value==text.value){
                        passwordss.nextElementSibling.innerHTML="密码支持数字和字母，位数至少为四位";
                        passwordss.nextElementSibling.style.color="blue";
                    }
                }
            }

            pwdsonblur=function(text,password){
                debugger;
                if(text.value==""){
                    text.nextElementSibling.innerHTML="密码不能为空";
                    text.nextElementSibling.style.color="red";
                }else if((text.value!="")&&(text.value.length<4)){
                    text.nextElementSibling.innerHTML="密码长度错误";
                    text.nextElementSibling.style.color="red";
                }else if(!ureg.test(text.value)){
                    text.nextElementSibling.innerHTML="密码格式输入错误";
                    text.nextElementSibling.style.color="red";
                }else if(password && password.value && password.value!=text.value){
                    text.nextElementSibling.innerHTML="两次密码不一致";
                    text.nextElementSibling.style.color="red";
                }else{
                    text.nextElementSibling.innerHTML="密码支持数字和字母，位数至少为四位";
                    text.nextElementSibling.style.color="blue";
                    if(password && password.value && password.value==text.value){
                        password.nextElementSibling.innerHTML="密码支持数字和字母，位数至少为四位";
                        password.nextElementSibling.style.color="blue";
                    }
                }
            }

            telonblur=function(text){
                if(text.value==""){
                    text.nextElementSibling.innerHTML="手机号码不能为空";
                    text.nextElementSibling.style.color="red";
                }else if(!treg.test(text.value)){
                    text.nextElementSibling.innerHTML="手机号码错误";
                    text.nextElementSibling.style.color="red";
                }else{
                    text.nextElementSibling.innerHTML="请输入11位手机号码";
                    text.nextElementSibling.style.color="blue";
                }
            }

            emailonblur=function(text){
                if(text.value==""){
                    text.nextElementSibling.innerHTML="邮箱输入错误";
                    text.nextElementSibling.style.color="red";
                }else if(!ereg.test(text.value)){
                    text.nextElementSibling.innerHTML="邮箱输入错误";
                    text.nextElementSibling.style.color="red";
                }else{
                    text.nextElementSibling.innerHTML="邮箱后面必须带有@号";
                    text.nextElementSibling.style.color="blue";
                }
            }

            // picyzm.onclick=function(){
            //     var num=Array();
            //     var str="";
            //     for(var i=0;i<4;i++){
            //         num[i]=Math.floor(Math.random()*10);
            //         str+=num[i];
            //     }
            //     picyzm.innerHTML=str;
            // }

            // yzm.onblur=function(){
            //     if(yzm.value==""){
            //         yzm.style.border="1px solid red";
            //         span6.innerHTML="验证码不能为空";
            //         span6.style.color="red";
            //     }else if(yzm.value!=picyzm.innerHTML){
            //         yzm.style.border="1px solid red";
            //         span6.innerHTML="验证码输入错误"
            //         span6.style.color="red";
            //     }else{
            //         yzm.style.border="1px solid #ccc";
            //     }
            // }

            // zhuce.onclick=function(){
            //     if((ureg.test(username.value))&&(ureg.test(pwd.value))&&(ureg.test(pwds.value))
            //     &&(treg.test(tel.value))&&(ereg.test(email.value))&&(yzm.value==picyzm.innerHTML)){
            //         zhuce.style.background="red";
            //     }
            // }