<script lang="ts">
    export let errMsg:string = "";
</script>

<div>
    <div class="padding-top-50 display-flex align-items justify-content-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 20 20" fill="#F24E1E">
            <path d="M7 20C6.4 20 6 19.6 6 19V16H2C0.9 16 0 15.1 0 14V2C0 0.9 0.9 0 2 0H18C19.1 0 20 0.9 20 2V14C20 15.1 19.1 16 18 16H11.9L8.2 19.7C8 19.9 7.8 20 7.5 20H7ZM11 9V3H9V9M11 13V11H9V13H11Z"/>
        </svg>
    </div>
    <div class="mt-20 ta-c">
        <p class="fs-18 pretendard-regular">{errMsg}</p>
    </div>
    
</div>

<style>
    .padding-top-50 {padding-top: 50px;}
</style>