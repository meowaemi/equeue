CREATE TABLE branch (
    id              UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(255)        NOT NULL,
    address         VARCHAR(255)        NOT NULL,
    created_at       TIMESTAMPTZ        NOT NULL
);

CREATE TABLE service_type (
    id                      UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    branch_id               UUID            NOT NULL REFERENCES branch (id),
    name                    VARCHAR(255)    NOT NULL,
    prefix                  VARCHAR(50)     NOT NULL,
    avg_service_minutes     INT             NOT NULL DEFAULT 10,
    active                  BOOLEAN         NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_service_type_branch_prefix UNIQUE (branch_id, prefix)
);

CREATE TABLE counter (
    id              UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    branch_id       UUID                NOT NULL REFERENCES branch (id),
    number          INT                 NOT NULL,
    status          VARCHAR(255)        NOT NULL,
    CONSTRAINT uq_counter_branch_number UNIQUE (branch_id, number),
    CONSTRAINT chk_counter_status CHECK (status IN ('OPEN', 'CLOSED', 'BREAK'))
);

CREATE TABLE counter_service_type (
    counter_id          UUID                REFERENCES counter (id) ON DELETE CASCADE,
    service_type_id     UUID                NOT NULL REFERENCES service_type (id) ON DELETE CASCADE,
    PRIMARY KEY (counter_id, service_type_id)
);

CREATE INDEX idx_counter_service_type_service ON counter_service_type (service_type_id);

CREATE TABLE ticket (
    id                  UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    branch_id           UUID                NOT NULL REFERENCES branch (id),
    service_type_id     UUID                NOT NULL REFERENCES service_type (id),
    number              VARCHAR(255)        NOT NULL,
    status              VARCHAR(255)        NOT NULL,
    priority            INT                 NOT NULL,
    counter_id          UUID                REFERENCES counter (id),
    created_at          TIMESTAMPTZ         NOT NULL,
    called_at           TIMESTAMPTZ,
    started_at          TIMESTAMPTZ,
    served_at           TIMESTAMPTZ,
    version             BIGINT              NOT NULL,
    CONSTRAINT chk_ticket_status CHECK (
        status IN ('WAITING', 'CALLED', 'IN_SERVICE', 'SERVED', 'MISSED', 'CANCELLED')
    )
);

CREATE INDEX idx_ticket_branch_status ON ticket (branch_id, status);
CREATE INDEX idx_ticket_service_status ON ticket (service_type_id, status);
CREATE INDEX idx_ticket_counter ON ticket (counter_id);