(function () {
  // Defensive: wait for DOMContentLoaded and log clear errors if DOM targets are missing
  function safeInit() {
    const matchesEl = document.getElementById('matches');
    const detailTitle = document.getElementById('detailTitle');
    const detailScore = document.getElementById('detailScore');
    const detailStatus = document.getElementById('detailStatus');
    const video = document.getElementById('video');
    const videoSource = document.getElementById('videoSource');

    const required = { matchesEl, detailTitle, detailScore, detailStatus, video, videoSource };
    const missing = Object.entries(required).filter(([, el]) => !el).map(([k]) => k);
    if (missing.length) {
      console.error('[Preview] DOM initialization failed. Missing elements:', missing.join(', '));
      return;
    }

    // Demo data: uses HTTPS video URLs and no parent-directory assets
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

    function renderCard(match) {
      const card = document.createElement('button');
      card.className = 'card';
      card.setAttribute('tabindex', '0');
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
      card.addEventListener('click', () => selectMatch(match));
      return card;
    }

    function selectMatch(m) {
      detailTitle.textContent = `${m.home.name} vs ${m.away.name}`;
      detailScore.textContent = `${m.score.home} - ${m.score.away}`;
      detailStatus.textContent = m.status;

      // Only set HTTPS or relative URLs; log if invalid
      const url = m.highlight && typeof m.highlight.videoUrl === 'string' ? m.highlight.videoUrl.trim() : '';
      if (url) {
        const isAbsoluteHttps = /^https:\/\//i.test(url);
        const isRelative = !/^[a-zA-Z]+:\/\//.test(url) && !url.startsWith('..');
        if (isAbsoluteHttps || isRelative) {
          videoSource.src = url;
          video.load();
          video.play().catch((err) => {
            console.warn('[Preview] Autoplay prevented or failed:', err?.message || err);
          });
        } else {
          console.error('[Preview] Invalid video URL (must be HTTPS or relative path):', url);
          // Clear source to avoid broken state
          videoSource.src = '';
          video.load();
        }
      } else {
        // No highlight: clear source
        videoSource.src = '';
        video.load();
      }
    }

    function initUI() {
      demoMatches.forEach((m) => matchesEl.appendChild(renderCard(m)));
      const first = matchesEl.querySelector('.card');
      if (first) {
        first.focus();
        selectMatch(demoMatches[0]);
      }
    }

    // Example fetch scaffold with defensive logging (no external fetch used by default)
    async function tryFetchDemo(url, options) {
      try {
        const res = await fetch(url, options);
        if (!res.ok) {
          console.error('[Preview] Fetch failed:', url, 'status:', res.status);
          return null;
        }
        return await res.json();
      } catch (e) {
        console.error('[Preview] Network error during fetch:', url, e?.message || e);
        return null;
      }
    }

    initUI();

    // If you later fetch remote demo data, use tryFetchDemo and preserve path safety.
    // e.g.:
    // tryFetchDemo('./demo.json').then(data => { ... }).catch(() => {});
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', safeInit, { once: true });
  } else {
    safeInit();
  }
})();
