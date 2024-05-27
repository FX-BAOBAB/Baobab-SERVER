    public AccountEntity getUser(Long userId) {
        return accountRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
