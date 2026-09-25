<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<@layout.mainLayout title=springMacroRequestContext.getMessage('page.chat.title')>
<div class="row justify-content-center">
    <div class="col-md-8">
        <a href="/responses" class="btn btn-outline-secondary btn-sm mb-3">← <@spring.message "chat.backToResponses"/></a>

        <div class="card mb-3 shadow-sm">
            <div class="card-body d-flex align-items-center gap-3">
                <#if chat.counterpartAvatarUrl?? && chat.counterpartAvatarUrl?has_content>
                <img src="/uploads/avatars/${chat.counterpartAvatarUrl}" alt="${chat.counterpartName!''}"
                     onerror="this.onerror=null;this.src='/images/default-avatar.svg';"
                     class="rounded-circle img-thumbnail" style="width: 64px; height: 64px; object-fit: cover;">
                <#else>
                <img src="/images/default-avatar.svg" alt="${chat.counterpartName!''}"
                     class="rounded-circle img-thumbnail" style="width: 64px; height: 64px; object-fit: cover;">
            </#if>
            <div>
                <h5 class="mb-1">${chat.counterpartName!springMacroRequestContext.getMessage('common.notSpecified')}</h5>
                <div class="text-muted small">
                    <#if chat.counterpartEmail?? && chat.counterpartEmail?has_content>
                    <span><@spring.message "chat.contacts.email"/> ${chat.counterpartEmail}</span>
                </#if>
                    <#if chat.counterpartPhone?? && chat.counterpartPhone?has_content>
                    <span class="ms-2"><@spring.message "chat.contacts.phone"/> ${chat.counterpartPhone}</span>
                </#if>
                </div>
                <div class="text-muted small mt-1">
                    <@spring.message "chat.about.vacancy"/> ${chat.vacancyTitle!''}
                    &middot;
                    <@spring.message "chat.about.resume"/> ${chat.resumeTitle!''}
                </div>
            </div>
            <span id="connectionStatus" class="badge bg-secondary ms-auto"><@spring.message "chat.connecting"/></span>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <div id="messagesContainer" class="d-flex flex-column gap-2 mb-3" style="height: 50vh; overflow-y: auto;">
                <#if chat.messages?? && chat.messages?has_content>
                <#list chat.messages as m>
                <div class="chat-message d-flex flex-column <#if m.senderId?? && m.senderId == currentUserId>align-items-end<#else>align-items-start</#if>">
                    <div class="px-3 py-2 rounded-3 <#if m.senderId?? && m.senderId == currentUserId>bg-primary text-white<#else>bg-light border</#if>" style="max-width: 75%; white-space: pre-line;">${m.content!''}</div>
                    <small class="text-muted mt-1">${m.senderName!''} &middot; <#if m.timestamp?? && m.timestamp?is_date_like>${m.timestamp?string("dd.MM.yyyy HH:mm")}<#else>${m.timestamp!''}</#if></small>
                </div>
                </#list>
                <#else>
                <p id="emptyPlaceholder" class="text-muted text-center mt-4"><@spring.message "chat.empty"/></p>
            </#if>
            </div>

            <form id="chatForm" class="d-flex gap-2">
                <input type="text" id="messageInput" class="form-control" placeholder="<@spring.message "chat.message.placeholder"/>" autocomplete="off">
                <button type="submit" class="btn btn-primary"><@spring.message "chat.send"/></button>
            </form>
        </div>
    </div>
</div>
</div>

<div id="chatConfig"
     data-response-id="${chat.responseId?c}"
     data-current-user-id="${currentUserId?c}"
     data-label-connected="<@spring.message "chat.connected"/>"
     data-label-disconnected="<@spring.message "chat.disconnected"/>"
     hidden></div>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.6.1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js"></script>
<script src="/js/chat.js"></script>
</@layout.mainLayout>
