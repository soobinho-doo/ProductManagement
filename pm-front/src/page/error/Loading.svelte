<div class="loading-box">
    <svg xmlns="http://www.w3.org/2000/svg" class="loading-img" viewBox="0 0 24 24">
        <circle cx="18" cy="12" r="0" fill="#2A3F54">
            <animate attributeName="r" begin=".67" calcMode="spline" dur="1.5s" keySplines="0.2 0.2 0.4 0.8;0.2 0.2 0.4 0.8;0.2 0.2 0.4 0.8" repeatCount="indefinite" values="0;2;0;0"/>
        </circle><circle cx="12" cy="12" r="0" fill="#2A3F54">
            <animate attributeName="r" begin=".33" calcMode="spline" dur="1.5s" keySplines="0.2 0.2 0.4 0.8;0.2 0.2 0.4 0.8;0.2 0.2 0.4 0.8" repeatCount="indefinite" values="0;2;0;0"/>
        </circle>
        <circle cx="6" cy="12" r="0" fill="#2A3F54">
            <animate attributeName="r" begin="0" calcMode="spline" dur="1.5s" keySplines="0.2 0.2 0.4 0.8;0.2 0.2 0.4 0.8;0.2 0.2 0.4 0.8" repeatCount="indefinite" values="0;2;0;0"/>
        </circle>
    </svg>
</div>

<style>
    .loading-box { position: relative; height: 100%;}
    .loading-img { position:absolute; top:50%; left:50%; transform:translate(-50%,50%); width: 150px; height: 150px; }
    @media all and (max-width: 480px){
        .loading-img {width: 130px; height: 130px;}
    }
</style>