let selected = null;
const purchase = {
          type: 'fdc3.purchase',
          data: {
            amount: 30,
            vendor: 'My Favorite Vendor',
            time: new Date().toLocaleTimeString(),
            date: new Date().toLocaleDateString(),
            userID: 'nick@connectifi.co',
            pointOfSale: 'POS1',
            category: 'Groceries' 
          }
  };


const selectCard = (id) => {
  const modal = document.getElementById('modal');
  const cards = modal.querySelectorAll('.card');
  cards.forEach((card) => {
    card.classList.remove('selected');
  });
  const selectedCard = document.getElementById(id);
  selectedCard?.classList.add('selected');
  selected = id;
  const purchaseButton = modal.querySelector('#purchaseButton');
  if (purchaseButton){
    purchaseButton.disabled = false;
  }
};

const launchBankApp = (index) => {
    window.open(`./bank-app${index}.html`, '_blank');
};

const showSuccessModal = (message, purchaseResult) => {
    const modal = document.getElementById('successModal');
    const modalCTA = document.getElementById('successCTA');
    modalCTA.addEventListener('click', () => { hideModal('successModal');});
    if (purchaseResult.provider.logo){
      const logoContainer = modal.querySelector('.logo');
      if (logoContainer){
          let target = logoContainer.querySelector('img');
          if (!target){
              target = document.createElement('img');
              logoContainer.appendChild(target);
          }
          target.src = purchaseResult.provider.logo;
      }
    }
    const modalText = modal.querySelector('.textContainer .text');
    modalText.textContent = message;
    showModal('successModal');
}

const initializeModal = () => {
  const modal = document.getElementById('modal');
  modal.innerHTML = '';

  const headline = document.createElement('div');
  headline.classList.add('headline');
  headline.classList.add('row');
  const headlineText = document.createElement('div');
  headlineText.classList.add('text');
  headlineText.textContent = 'Promotional Offers';
  headline.appendChild(headlineText);
  modal.appendChild(headline);

  const subhead = document.createElement('div');
  subhead.classList.add('subhead');
  subhead.classList.add('row');
  const subheadText = document.createElement('div');
  subheadText.classList.add('text');
  subheadText.textContent = 'Select the promotional offer you would like to use';
  subhead.appendChild(subheadText);
  modal.appendChild(subhead);

  const cardContainer = document.createElement('div');
  cardContainer.classList.add('cards');
  cardContainer.classList.add('headline');
  modal.appendChild(cardContainer);

  const actionRow = document.createElement('div');
 actionRow.classList.add('row');
 actionRow.classList.add('actions');
 const purchaseButton = document.createElement('button');
 purchaseButton.id = 'purchaseButton';
 purchaseButton.disabled = true;
 purchaseButton.textContent = 'Make Purchase';
 purchaseButton.addEventListener('click', async () => {
  const purchaseResponse = await fdc3?.raiseIntent('MakePurchase', purchase, selected);
  const purchaseResult = await purchaseResponse.getResult();
  hideModal();
  showSuccessModal('Purchase Successful', purchaseResult.data);
 });
actionRow.appendChild(purchaseButton);
modal.appendChild(actionRow);

const notificationRow = document.createElement('div');
notificationRow.classList.add('row');
notificationRow.classList.add('notifications');

modal.appendChild(notificationRow);
};

const showModal = (type) => {
  const modal = document.getElementById(type || 'modal');
  modal?.classList.add('show');
};

const hideModal = (type) => {
  const modal = document.getElementById(type || 'modal');
  modal?.classList.remove('show');
};

const renderBankResult = (data) => {
  const modal = document.getElementById('modal');
  const cardContainer = modal?.querySelector('.cards');
  if (!cardContainer){
    return;
  }
  const bankCard = document.createElement('div');
  bankCard.id = data.provider.id;
  bankCard.addEventListener('click', () => { selectCard(data.provider.id)});
  bankCard.classList.add('card');
  const cardHeader = document.createElement('div');
  cardHeader.classList.add('header');
  const headerText = document.createElement('div');
  if (data.provider.logo) {
    headerText.classList.add('logo');
    const logo = document.createElement('img');
    logo.src = data.provider.logo;
    logo.title = data.provider.name;
    headerText.appendChild(logo);
  } else {
    headerText.classList.add('text');  
    headerText.textContent = data.provider;
  }
  cardHeader.appendChild(headerText);
  bankCard.appendChild(cardHeader);

  const contentContainer = document.createElement('div');
  contentContainer.classList.add('content');

  const pointsRow = document.createElement('div');
  pointsRow.classList.add('row');
  pointsRow.classList.add('points');
  const pointsText = document.createElement('div');
  pointsText.classList.add('text');
  pointsText.textContent = `${data.points} points`;
  pointsRow.appendChild(pointsText);
  contentContainer.appendChild(pointsRow);

  const ratesRow = document.createElement('div');
  ratesRow.classList.add('row');
  ratesRow.classList.add('rates');
  const ratesText = document.createElement('div');
  ratesText.classList.add('text');
  ratesText.textContent = `@Rate of ${data.rate}`;
  ratesRow.appendChild(ratesText);
  contentContainer.appendChild(ratesRow);

  bankCard.appendChild(contentContainer);
  cardContainer.appendChild(bankCard);     
}

const getTerms = async () => {
  const appIntents = await fdc3.findIntent('GetTerms',purchase);
  initializeModal();
  appIntents.apps.forEach(async (app) => {
      const result = await fdc3.raiseIntent('GetTerms', purchase, {appId: app.appId});
      const contextData = await result.getResult();            
      renderBankResult(contextData.data);
  });
  showModal();
};