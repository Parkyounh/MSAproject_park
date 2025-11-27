// static/menu-list.js

// Bootstrap 모달 객체를 저장할 변수
let menuDetailModalInstance = null;
let currentMenuBasePrice = 0; // 현재 선택된 메뉴의 기본 가격 (숫자)

// ==========================================================
// 1. 유틸리티 함수
// ==========================================================

/**
 * 숫자를 쉼표 형식으로 포맷합니다. (예: 4200 -> 4,200)
 * @param {number} number
 */
function formatNumber(number) {
    if (typeof number !== 'number' || isNaN(number)) {
        return '0';
    }
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// ==========================================================
// 2. 모달 제어 함수
// ==========================================================

/**
 * 메뉴 아이템 클릭 시 모달을 열고 데이터를 설정합니다.
 * @param {HTMLElement} element - 클릭된 메뉴 카드 div
 */
function openMenuDetailModal(element) {

    // 1. 메뉴 기본 정보 가져오기 (HTML data 속성 및 내부 텍스트에서 가져옴)
    const menuCode = element.getAttribute('data-menu-code');
    const menuName = element.querySelector('.card-title').innerText;
    const basePriceText = element.querySelector('.card-text').innerText;

    // 가격 문자열에서 '원'과 쉼표를 제거하고 숫자로 변환
    currentMenuBasePrice = parseInt(basePriceText.replace(/[^0-9]/g, ''));

    // 2. 모달 인스턴스 초기화 및 열기
    if (!menuDetailModalInstance) {
        const modalElement = document.getElementById('menuDetailModal');
        // Bootstrap 5.x 모달 초기화
        menuDetailModalInstance = new bootstrap.Modal(modalElement);
    }

    // 3. 모달에 데이터 채우기

    // 🌟 이미지 경로 설정: '/images/{menuCode}.jpg'
    document.getElementById('modalMenuImage').src = `/images/${menuCode}.jpg`;
    document.getElementById('modalMenuImage').alt = menuName + ' 이미지';

    document.getElementById('modalMenuName').innerText = menuName;
    document.getElementById('modalBasePrice').innerText = basePriceText;

    // 4. 수량 및 옵션 초기화
    document.getElementById('modalQuantity').value = 1;
    document.getElementById('modalTotalPrice').setAttribute('data-base-price', currentMenuBasePrice);

    // 옵션 체크박스 초기화
    document.querySelectorAll('#optionsContainer input[type="checkbox"]').forEach(checkbox => {
        checkbox.checked = false;
    });

    // 5. 총 가격 업데이트 및 모달 표시
    updateTotalPriceDisplay();
    menuDetailModalInstance.show();
}

/**
 * 수량 변경 버튼 ( + / - ) 클릭 핸들러
 * @param {number} delta - 1 또는 -1
 */
function changeQuantity(delta) {
    const quantityInput = document.getElementById('modalQuantity');
    let quantity = parseInt(quantityInput.value);

    // 수량 업데이트 및 최소 수량 1 제한
    quantity = Math.max(1, quantity + delta);
    quantityInput.value = quantity;

    updateTotalPriceDisplay();
}

/**
 * 옵션 선택, 수량 변경 시 총 주문 금액을 계산하고 표시합니다.
 */
function updateTotalPriceDisplay() {
    let totalPrice = currentMenuBasePrice;

    // 1. 옵션 가격 합산
    document.querySelectorAll('.option-input:checked').forEach(checkbox => {
        // data-price-delta 속성에서 추가 가격을 가져옴
        const priceDelta = parseInt(checkbox.getAttribute('data-price-delta')) || 0;
        totalPrice += priceDelta;
    });

    // 2. 수량 곱하기
    const quantity = parseInt(document.getElementById('modalQuantity').value) || 1;
    totalPrice *= quantity;

    // 3. 금액 표시 업데이트
    document.getElementById('modalTotalPrice').innerText = formatNumber(totalPrice) + '원';
}


// ==========================================================
// 3. 주문/장바구니 핸들러
// ==========================================================

function addToCart() {
    alert("장바구니에 추가되었습니다! (총 금액: " + document.getElementById('modalTotalPrice').innerText + ")");
    // 여기에 실제 장바구니 처리 로직 (AJAX) 추가
}

function placeOrder() {
    alert("바로 주문 요청! (총 금액: " + document.getElementById('modalTotalPrice').innerText + ")");
    // 여기에 실제 주문 처리 로직 (AJAX) 추가
}