import { getApi, postApi, putApi, delApi } from "./api";
import { noti } from "./store";
import { writable, get, derived } from "svelte/store";
import { replace } from "svelte-spa-router";

//
function setAuth () {
    let initDatas = {
        user_id:'',
        user_email : '',
        user_auth:'', 
        Authorization : '',
    }

    const {subscribe, set, update} = writable({...initDatas})

    const refresh = async () => {
        try{
            const getDatas = await getApi({path:'/api/user/again-token'})
            set(getDatas)
            isRefresh.set(true)
        }catch(err){
            auth.resetUserInfo();
            isRefresh.set(false)
        }
    }
    const resetUserInfo = () => {set({...initDatas})}
    const login = async (data:any) => {
        try{
            const options = {
                path : '/api/user/login', 
                data : data,
            }
            const loginInfo = await postApi(options)
            set(loginInfo)
            isRefresh.set(true)
            replace('/')
            noti.info('로그인 완료', 2000);

        }catch(err){
            alert("오류가 발생하였습니다. 다시 시도해 주세요")
        }
    }
    const logout = async () => {
        try{
            const options = {
                path: '/api/user/logout',
            }
            await getApi(options);
            set({...initDatas})
            isRefresh.set(false);
            replace('/')
            noti.info('로그아웃 완료', 2000);
        }catch(err){
            alert("오류가 발생하였습니다 다시시도해 주세요")
        }
    }

    return {
        subscribe,
        refresh,
        resetUserInfo,
        login,
        logout,
    }
}

function setIsLogin() {
    const checkLogin = derived(auth, $auth => $auth.Authorization ? true : false)
    return checkLogin
}

export const auth = setAuth();
export const isRefresh = writable(false);
export const isLogin = setIsLogin();