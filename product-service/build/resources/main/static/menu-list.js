/**
 * menu-list.js
 * 상품 목록 페이지 (menu-list.html)에서 상품 상세 모달(팝업) 관련 로직을 처리합니다.
 */

// 부트스트랩 모달 인스턴스를 저장하기 위한 변수
let menuDetailModalInstance;

// 1. DOMContentLoaded 이벤트: DOM이 완전히 로드된 후 모달 인스턴스를 초기화합니다.
document.addEventListener('DOMContentLoaded', function() {
    const menuDetailModalElement = document.getElementById('menuDetailModal');

    if (menuDetailModalElement) {
        // ID가 'menuDetailModal'인 요소를 찾았다면 인스턴스 생성
        menuDetailModalInstance = new bootstrap.Modal(menuDetailModalElement);
        console.log("✅ 모달 인스턴스 초기화 완료");
    } else {
        // 모달 요소를 찾지 못했을 경우 (디버깅용)
        console.error("❌ 'menuDetailModal' ID를 가진 요소를 찾을 수 없습니다.");
    }

    // 수량 입력 필드 변경 시 총 가격 업데이트 이벤트 리스너 추가 (직접 입력 방지 겸용)
    const qtyInput = document.getElementById('modalQuantity');
    if (qtyInput) {
        qtyInput.addEventListener('change', updateTotalPriceDisplay);
        // 수량 직접 입력 시 최소값 1을 강제
        qtyInput.addEventListener('input', function() {
            if (parseInt(this.value) < 1) {
                this.value = 1;
            }
        });
    }
});


// 2. 상품 클릭 시 호출되는 함수 (HTML의 onclick="openMenuDetailModal(this)"와 연결)
function openMenuDetailModal(element) {
    const menuCode = element.getAttribute('data-menu-code');

    if (!menuDetailModalInstance) {
        const menuDetailModalElement = document.getElementById('menuDetailModal');
        if (menuDetailModalElement) {
            menuDetailModalInstance = new bootstrap.Modal(menuDetailModalElement);
            console.log("✅ 모달 인스턴스 지연 초기화 완료");
        } else {
            // 모달 HTML 요소 자체가 없으면 경고 후 종료
            alert("모달 HTML 요소가 없습니다. 관리자에게 문의하세요.");
            return;
        }
    }

    // --- 실제 구현 시, 여기에서 menuCode를 이용해 서버에 상세 정보를 AJAX 요청합니다. ---

    // --- 더미 데이터 로딩 로직 시작 (실제 데이터 로딩 전까지 사용) ---
    // 클릭된 메뉴에 따라 데이터를 채워야 하지만, 현재는 임시로 아메리카노 데이터를 사용합니다.
    let basePrice = 4000;

    // 메뉴 이름, 가격, 설명 업데이트
    document.getElementById('modalMenuName').innerText = '아메리카노 (Ice)';
    document.getElementById('modalBasePrice').innerText = basePrice.toLocaleString() + '원';
    document.getElementById('modalDescription').innerText = '진하고 풍부한 맛의 기본 에스프레소 커피입니다.';

    // 옵션 목록 동적 생성 (실제 구현 시 서버 데이터 기반으로 생성)
    const optionsContainer = document.getElementById('optionsContainer');
    optionsContainer.innerHTML = `
        <div class="mb-3">
            <h6>사이즈 선택 (필수)</h6>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="size" id="sizeTall" value="Tall" data-price-delta="0" checked onchange="updateTotalPriceDisplay()">
                <label class="form-check-label" for="sizeTall">Tall (기본)</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="size" id="sizeGrande" value="Grande" data-price-delta="1000" onchange="updateTotalPriceDisplay()">
                <label class="form-check-label" for="sizeGrande">Grande (+1,000원)</label>
            </div>
        </div>
        <div class="mb-3">
            <h6>추가 옵션 (선택)</h6>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="addShot" data-price-delta="500" onchange="updateTotalPriceDisplay()">
                <label class="form-check-label" for="addShot">샷 추가 (+500원)</label>
            </div>
        </div>
    `;

    // 초기 가격 설정 및 수량 초기화
    document.getElementById('modalQuantity').value = 1;
    document.getElementById('modalTotalPrice').setAttribute('data-base-price', basePrice);

    // 총 가격 업데이트 및 모달 표시
    updateTotalPriceDisplay();
    menuDetailModalInstance.show();
    console.log(`메뉴 코드 ${menuCode}의 상세 모달 표시.`);
}


// 3. 최종 가격을 계산하고 표시하는 함수 (옵션/수량 변경 시 호출됨)
function updateTotalPriceDisplay() {
    // 1. 기본 가격 및 수량 가져오기
    let basePrice = parseInt(document.getElementById('modalTotalPrice').getAttribute('data-base-price') || 0);
    let quantity = parseInt(document.getElementById('modalQuantity').value || 1);

    // 수량 유효성 검사
    if (quantity < 1) {
        quantity = 1;
        document.getElementById('modalQuantity').value = 1;
    }

    let optionPrice = 0;

    // 2. 옵션 가격 계산 (라디오 및 체크박스)
    // 모든 옵션 요소를 순회하며 data-price-delta 값을 합산
    document.querySelectorAll('#optionsContainer input').forEach(input => {
        if ((input.type === 'radio' && input.checked) || (input.type === 'checkbox' && input.checked)) {
            optionPrice += parseInt(input.getAttribute('data-price-delta') || 0);
        }
    });

    // 3. 총 금액 계산
    const finalPrice = (basePrice + optionPrice) * quantity;

    // 4. 금액 표시 업데이트
    document.getElementById('modalTotalPrice').innerText = finalPrice.toLocaleString() + '원';
}


// 4. 수량을 변경하는 함수 (+/- 버튼 클릭 시 호출됨)
function changeQuantity(delta) {
    const qtyInput = document.getElementById('modalQuantity');
    let newQty = parseInt(qtyInput.value) + delta;

    // 수량은 최소 1
    if (newQty < 1) newQty = 1;

    qtyInput.value = newQty;
    updateTotalPriceDisplay(); // 수량이 변경되면 총 가격 업데이트
}