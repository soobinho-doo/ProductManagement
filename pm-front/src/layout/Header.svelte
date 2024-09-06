<script lang="ts">
    import DownMenuBar from "./DownMenuBar.svelte";
    import { account } from "../option/account";
    import { link, push } from "svelte-spa-router";
    import DropDown from "../component/DropDown.svelte";
    import { auth } from "../option/auth";

    //

    // Method
    // Logout
    const logout = async () => {
        await auth.logout();
    }
    // UserInfo
    let userName:string = "";
    const getUserInfo = async () => {
        let getDatas = await account.info();
        userName = getDatas.user_nm
    }

    //
    let isToggle:boolean = false;
    const isSideHide = () => {
        let content:any = document.querySelector(".side-menu-bar:not(.side-menu-bar-end)~*")
        let sideHide:any = document.querySelector(".side-menu-bar:not(.side-menu-bar-end):not(.show)");
        
        isToggle = !isToggle;
        if(isToggle){
            sideHide.style.setProperty("margin-left", "calc(var(--cal-side-menu-bar-width)*-1)");
            content.style.setProperty("--cul-side-menu-bar-start", "0");
        }else{
            sideHide.style.removeProperty("margin-left");
            content.style.setProperty("--cul-side-menu-bar-start", "16rem");
        }
        
        
    }

</script>

<header class="height-50px display-flex align-items justify-content-between border-bottom-b1 background-color-white">
    <div class="padding-12">
        <a class="brand fs-18 pretendard-bold" href="/" use:link>Product Management</a>
        <button type="button" class="not-show border-none background-none" on:click={isSideHide}>
            <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="24" height="24" viewBox="0 0 24 24">
                <path fill="currentColor" d="M4 6h16v2H4V6zm0 5h16v2H4v-2zm16 5H4v2h16v-2z"/>
            </svg>
        </button>
    </div>

    <div class="display-flex align-items gap-20">
    
            <DropDown>
                <div class="display-flex align-items gap-5 padding-12-16" slot="select-title">
                    {#await getUserInfo() then }
                        <span class="fs-14 pretendard-regular fw-b">{userName}</span>
                    {/await}
                    <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="28" height="28" viewBox="0 0 24 24">
                        <path fill="currentColor" d="M5 18.235q1.35-1.325 3.138-2.088T12 15.385t3.863.762T19 18.235V5.615q0-.23-.192-.423T18.384 5H5.616q-.231 0-.424.192T5 5.616zm7-5.158q1.258 0 2.129-.871T15 10.077t-.871-2.129T12 7.077t-2.129.871T9 10.077t.871 2.129t2.129.871M5.616 20q-.691 0-1.153-.462T4 18.384V5.616q0-.691.463-1.153T5.616 4h12.769q.69 0 1.153.463T20 5.616v12.769q0 .69-.462 1.153T18.384 20zm.013-1H18.37q-1.446-1.36-3.064-1.988T12 16.385q-1.65 0-3.293.628T5.629 19M12 12.077q-.817 0-1.409-.591Q10 10.894 10 10.077t.591-1.409q.592-.591 1.409-.591t1.409.591Q14 9.26 14 10.077t-.591 1.409t-1.409.591m0-.46"/>
                    </svg>
                </div>
                
                <ul class="select-content ta-l background-color-white border-b1" slot="select-item"> 
                    <li class="display-flex align-items gap-5 button-hover padding-12-6" role="presentation" on:click={()=>{push("/mypage")}}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change"  width="16" height="16" viewBox="0 0 24 24">
                            <path d="M17 3h-3v2h3v16H7V5h3V3H7a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V5a2 2 0 0 0-2-2m-5 4a2 2 0 0 1 2 2a2 2 0 0 1-2 2a2 2 0 0 1-2-2a2 2 0 0 1 2-2m4 8H8v-1c0-1.33 2.67-2 4-2s4 .67 4 2v1m0 3H8v-1h8v1m-4 2H8v-1h4v1m1-15h-2V1h2v4Z"/>
                        </svg>
                        <span class="fs-14 pretendard-regular">MyPage</span>
                    </li>
                    <li class="display-flex align-items gap-5 button-hover padding-12-6" role="presentation" on:click={()=>{push("/setting")}}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="16" height="16" viewBox="0 0 24 24">
                            <path d="m10.135 21l-.362-2.892q-.479-.145-1.035-.454q-.557-.31-.948-.664l-2.667 1.135l-1.865-3.25l2.305-1.738q-.044-.272-.073-.56q-.028-.287-.028-.558q0-.252.028-.53t.073-.626L3.258 9.125l1.865-3.212L7.771 7.03q.448-.373.97-.673q.52-.3 1.013-.464L10.134 3h3.731l.362 2.912q.575.201 1.016.463q.442.262.909.654l2.725-1.116l1.865 3.212l-2.382 1.796q.082.31.092.568q.01.26.01.511q0 .233-.02.491q-.019.259-.088.626l2.344 1.758l-1.865 3.25l-2.681-1.154q-.467.392-.94.673q-.474.281-.985.444L13.865 21h-3.73ZM11 20h1.956l.369-2.708q.756-.2 1.36-.549q.605-.349 1.232-.956l2.495 1.063l.994-1.7l-2.189-1.644q.125-.427.166-.786q.04-.358.04-.72q0-.38-.04-.72q-.04-.34-.166-.747l2.227-1.683l-.994-1.7l-2.552 1.07q-.454-.499-1.193-.934t-1.4-.578L13 4h-1.994l-.312 2.688q-.756.162-1.39.52q-.633.36-1.26.986L5.55 7.15l-.994 1.7l2.169 1.62q-.125.336-.175.73q-.05.394-.05.82q0 .38.05.755t.156.73l-2.15 1.645l.994 1.7l2.475-1.05q.588.594 1.222.953t1.428.559L11 20Zm.973-5.5q1.046 0 1.773-.727q.727-.727.727-1.773q0-1.046-.727-1.773q-.727-.727-1.773-.727q-1.052 0-1.776.727T9.473 12q0 1.046.724 1.773q.724.727 1.776.727ZM12 12Z"/>
                        </svg>
                        <span class="fs-14 pretendard-regular">설정</span>
                    </li>
                    <li class="display-flex align-items gap-5 button-hover padding-12-6" role="presentation" on:click={logout}>
                        <svg xmlns="http://www.w3.org/2000/svg" class="svg-color-change" width="16" height="16" viewBox="0 0 24 24">
                            <path d="M12 21v-2h7V5h-7V3h9v18h-9Zm-2-4l-1.375-1.45l2.55-2.55H3v-2h8.175l-2.55-2.55L10 7l5 5l-5 5Z"/>
                        </svg>
                        <span class="fs-14 pretendard-regular">로그아웃</span>
                    </li>
                </ul>
                
            </DropDown>
        
    </div>
</header>

<DownMenuBar/>

<style>
    header {position: sticky; top: 0;}
    .brand {display: none;}
    svg {vertical-align: middle;}

    @media all and (max-width: 768px){
        .brand {display: block;}
        .not-show {display: none;}
    }
</style>