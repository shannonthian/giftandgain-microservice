import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, translate } from 'react-jhipster';
import { toast } from 'react-toastify';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { ITargetInventory } from 'app/shared/model/target-inventory.model';
import { getEntity, deleteEntity } from './target-inventory.reducer';

export const TargetInventoryDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const targetInventoryEntity: ITargetInventory = useAppSelector(state => state.targetInventory.entity);
  const updateSuccess: boolean = useAppSelector(state => state.targetInventory.updateSuccess);

  const handleClose = () => {
    navigate('/target-inventory');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      toast.success(translate("giftandgainFrontendApp.targetInventory.deleted", { id }));
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(targetInventoryEntity.targetId));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="targetInventoryDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="giftandgainFrontendApp.targetInventory.delete.question">
        <Translate contentKey="giftandgainFrontendApp.targetInventory.delete.question" interpolate={{ id: targetInventoryEntity.targetId }}>
          Are you sure you want to delete this TargetInventory?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-targetInventory" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default TargetInventoryDeleteDialog;
