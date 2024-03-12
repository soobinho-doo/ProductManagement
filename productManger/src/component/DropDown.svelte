<script lang="ts">

    let isSelect:boolean = false;
    const selectClick = () => {
        isSelect = !isSelect;
    }

</script>

<button type="button" class="position-relative background-none border-none" on:click={selectClick} on:blur={() => {isSelect = false;}}>
    <div>
        <slot name="select-title"></slot>
    </div>
    <div class="not-show" class:show={isSelect}>
        <slot name="select-item"></slot>
    </div>
</button>

<style>

    .not-show {display: none; position:absolute; right: 0; width: 100%; z-index: 2;}
    .show {display: block;}

</style>