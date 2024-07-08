<script lang="ts">
    import { fly } from "svelte/transition";
    import { noti } from "../option/store";
    export let themes:any = {
        danger: "#E26D69",
        success: "#84C991",
        warning: "#f0ad4e",
        info: "#5bc0de",
        default: "#aaaaaa",
    };

</script>

<div class="noti">
    {#each $noti as notification (notification.id)}
        <div
            class="toast padding-12-16 border-radius-4"
            style="background: {themes[notification.type]};"
            transition:fly={{ y: 30 }}
        >
            <div class="fs-16 pretendard-regular text-white">{notification.message}</div>
        </div>
    {/each}
</div>

<style>
    .noti {
        position: fixed;
        left: 0;
        right: 0;
        bottom: 10rem;
        margin: 0 auto;
        padding: 0;
        z-index: 9999;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
        align-items: center;
    }

    .toast {
        flex: 0 0 auto;
        margin-bottom: 10px;
    }
</style>