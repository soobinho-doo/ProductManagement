<script lang="ts">
    import Router from "./Router.svelte";
    import LoadingPage from "./loading/LoadingPage.svelte";
    import axios from "axios";
    import { isConnection } from "./option/store";

    // 새로고침 시 Refresh Token 유무에 따라 Access Token 재발급 여부
    const againToken = async () => {
        await axios.post("/api/user/again-token").then((res) => {
            let isAccessToken = res.data;
            if(isAccessToken){
                axios.defaults.headers.common["Authorization"] = "Bearer "+isAccessToken;
                isConnection.set(true);
                const refreshInterval = setTimeout(() => {
                    if($isConnection){
                        againToken();
                    }else{
                        clearInterval(refreshInterval);
                    }
                }, 1000 * 60 * 29)

            }else{
                isConnection.set(false);
            }
        }).catch((err)=> {});
    }
    let promise:any = againToken();

</script>

{#await promise}
    <LoadingPage/>
{:then} 
    <Router/>
{:catch error}
    <div class="ta-c">
        <p class="fs-18 pretendard-bold">ERROR: {error.message}</p>
    </div>
{/await}