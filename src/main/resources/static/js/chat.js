(function () {
    var config = document.getElementById('chatConfig');
    if (!config) {
        return;
    }

    var responseId = Number(config.dataset.responseId);
    var currentUserId = Number(config.dataset.currentUserId);
    var labelConnected = config.dataset.labelConnected;
    var labelDisconnected = config.dataset.labelDisconnected;

    var messagesContainer = document.getElementById('messagesContainer');
    var emptyPlaceholder = document.getElementById('emptyPlaceholder');
    var statusBadge = document.getElementById('connectionStatus');
    var form = document.getElementById('chatForm');
    var input = document.getElementById('messageInput');

    function scrollToBottom() {
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }

    function formatTime(iso) {
        var d = new Date(iso);
        if (isNaN(d.getTime())) {
            return '';
        }
        var pad = function (n) { return String(n).padStart(2, '0'); };
        return pad(d.getDate()) + '.' + pad(d.getMonth() + 1) + '.' + d.getFullYear() + ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes());
    }

    function appendMessage(message) {
        if (emptyPlaceholder) {
            emptyPlaceholder.remove();
            emptyPlaceholder = null;
        }

        var isMine = message.senderId === currentUserId;

        var wrapper = document.createElement('div');
        wrapper.className = 'chat-message d-flex flex-column ' + (isMine ? 'align-items-end' : 'align-items-start');

        var bubble = document.createElement('div');
        bubble.className = 'px-3 py-2 rounded-3 ' + (isMine ? 'bg-primary text-white' : 'bg-light border');
        bubble.style.maxWidth = '75%';
        bubble.style.whiteSpace = 'pre-line';
        bubble.textContent = message.content;

        var meta = document.createElement('small');
        meta.className = 'text-muted mt-1';
        meta.textContent = (message.senderName || '') + ' · ' + formatTime(message.timestamp);

        wrapper.appendChild(bubble);
        wrapper.appendChild(meta);
        messagesContainer.appendChild(wrapper);
        scrollToBottom();
    }

    scrollToBottom();

    var socket = new SockJS('/ws');
    var stompClient = new StompJs.Client({
        webSocketFactory: function () { return socket; },
        reconnectDelay: 3000,
        onConnect: function () {
            statusBadge.textContent = labelConnected;
            statusBadge.className = 'badge bg-success ms-auto';
            stompClient.subscribe('/topic/chat/' + responseId, function (frame) {
                var message = JSON.parse(frame.body);
                appendMessage(message);
            });
        },
        onWebSocketClose: function () {
            statusBadge.textContent = labelDisconnected;
            statusBadge.className = 'badge bg-warning text-dark ms-auto';
        }
    });
    stompClient.activate();

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        var content = input.value.trim();
        if (!content || !stompClient.connected) {
            return;
        }
        stompClient.publish({
            destination: '/app/chat/' + responseId + '/send',
            body: JSON.stringify({ content: content })
        });
        input.value = '';
    });
})();
