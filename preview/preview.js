const matchesEl = document.getElementById('matches');
const detailTitle = document.getElementById('detailTitle');
const detailScore = document.getElementById('detailScore');
const detailStatus = document.getElementById('detailStatus');
const video = document.getElementById('video');
const videoSource = document.getElementById('videoSource');

const demoMatches = [
  {
    id: 'm1',
    home: { name: 'Sharks', logoUrl: '' },
    away: { name: 'Falcons', logoUrl: '' },
    score: { home: 2, away: 1 },
    status: 'Q3 08:13',
    highlight: { videoUrl: 'https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4' }
  },
  {
    id: 'm2',
    home: { name: 'Lions', logoUrl: '' },
    away: { name: 'Bulls', logoUrl: '' },
    score: { home: 89, away: 92 },
    status: 'Q4 01:02',
    highlight: { videoUrl: 'https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4' }
  },
  {
    id: 'm3',
    home: { name: 'Waves', logoUrl: '' },
    away: { name: 'Storm', logoUrl: '' },
    score: { home: 0, away: 0 },
    status: 'Starts 20:00',
    highlight: { videoUrl: '' }
  }
];

function renderCard(match){
  const card = document.createElement('button');
  card.className = 'card';
  card.setAttribute('tabindex','0');
  card.innerHTML = `
    <div class="row">
      <img width="56" height="56" alt="" />
      <div class="team">${match.home.name}</div>
      <div class="score">${match.score.home}</div>
    </div>
    <div class="row">
      <img width="56" height="56" alt="" />
      <div class="team">${match.away.name}</div>
      <div class="score">${match.score.away}</div>
    </div>
    <div class="status">${match.status}</div>
  `;
  card.addEventListener('click', ()=> selectMatch(match));
  return card;
}

function selectMatch(m){
  detailTitle.textContent = `${m.home.name} vs ${m.away.name}`;
  detailScore.textContent = `${m.score.home} - ${m.score.away}`;
  detailStatus.textContent = m.status;
  if (m.highlight && m.highlight.videoUrl){
    videoSource.src = m.highlight.videoUrl;
    video.load();
    video.play().catch(()=>{});
  }
}

function init(){
  demoMatches.forEach(m => matchesEl.appendChild(renderCard(m)));
  const first = matchesEl.querySelector('.card');
  if (first){ first.focus(); selectMatch(demoMatches[0]); }
}
init();
