import { delApi, getApi, patchApi, postApi, putApi } from "./api";
import { auth, isRefresh } from "./auth";
import { derived, get, writable } from "svelte/store";
import { noti } from "./store";
import { replace } from "svelte-spa-router";

//
function setAccount() {
    const info = async () => {
        const id = get(auth).id;
        const accessToken = get(auth).Authorization;

        try{
            const options = {
                path: '/api/user/info', 
                accessToken:accessToken
            }
            const getInfo:any = await postApi(options);
            return getInfo
        }catch(err){
            throw err
        }
    }
    const updateInfo = async () => {}
    const deleteInfo = async () => {
        const id = get(auth).id;
        const accessToken = get(auth).Authorization;

        try{
            const options = {
                path: '/api/user/'+id,
                data: {},
                accessToken:accessToken,
            }
            const delInfo = await delApi(options)
            if(delInfo === 1){
                auth.resetUserInfo();
                isRefresh.set(false)
                replace("/")
                noti.info("탈퇴 되셨습니다. 안녕히 가세요", 2500);
            }else{
                alert("회원탈퇴 에러발생, 다시 시도해주세요")
            }
        }catch(err){
            alert("회원탈퇴 에러발생, 다시 시도해주세요")
        }
    }

    // Return 
    return {
        info,
        updateInfo,
        deleteInfo,
    }
}

// Export 
export const account = setAccount();